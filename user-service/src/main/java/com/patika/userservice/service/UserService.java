package com.patika.userservice.service;

import com.patika.userservice.converter.UserConverter;
import com.patika.userservice.dto.request.AuthLoginRequest;
import com.patika.userservice.dto.request.AuthRegisterRequest;
import com.patika.userservice.dto.request.UserRequest;
import com.patika.userservice.dto.response.UserResponse;
import com.patika.userservice.dto.response.UserVO;
import com.patika.userservice.exception.UserException;
import com.patika.userservice.exception.ExceptionMessages;
import com.patika.userservice.log.LogMessages;
import com.patika.userservice.model.Role;
import com.patika.userservice.model.User;
import com.patika.userservice.model.enums.RoleType;
import com.patika.userservice.producer.KafkaProducer;
import com.patika.userservice.producer.RabbitMqProducer;
import com.patika.userservice.producer.dto.NotificationRequest;
import com.patika.userservice.producer.dto.enums.NotificationType;
import com.patika.userservice.repository.RoleRepository;
import com.patika.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;
    private final PasswordEncoder passwordEncoder;

    public UserVO getUserByEmailForAuth(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));

        return UserConverter.convertToResponseUserVO(user);
    }

    public UserVO createUserForAuth(AuthRegisterRequest request) {

        if (request.getEmail() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
            kafkaProducer.sendException(ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
            throw new UserException(ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
        }

        if (request.getUserType() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.USER_TYPE_CAN_NOT_BE_EMPTY);
            kafkaProducer.sendException(ExceptionMessages.USER_TYPE_CAN_NOT_BE_EMPTY);
            throw new UserException(ExceptionMessages.USER_TYPE_CAN_NOT_BE_EMPTY);
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            throw new UserException(ExceptionMessages.USER_ALREADY_DEFINED);
        } else {
            User savedUser = new User(request.getFirstName(), request.getLastName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
            savedUser.setUserType(request.getUserType());
            Role roleUser = roleRepository.findByRoleName(RoleType.USER).orElseThrow(() -> new UserException(ExceptionMessages.USER_ROLE_NOT_FOUND));
            Set<Role> roles = new HashSet<>();
            roles.add(roleUser);
            savedUser.setRoles(roles);
            userRepository.save(savedUser);

            String message = "Thank you for creating account.\n";
            rabbitMqProducer.sendNotification(new NotificationRequest(message, NotificationType.MAIL, savedUser.getEmail()));

            return UserConverter.convertToResponseUserVO(savedUser);
        }

    }

    public UserVO validateUserForAuth(AuthLoginRequest request) {

        if (request.getEmail() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
            kafkaProducer.sendException(ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
            throw new UserException(ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        } else {
            User foundUser = optionalUser.get();
            if (passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
                return UserConverter.convertToResponseUserVO(foundUser);
            } else {
                throw new UserException(ExceptionMessages.USER_PASSWORD_NOT_MATCHED);
            }
        }

    }

    public UserResponse updateUser(UserRequest request) {

        Optional<User> optionalUser = userRepository.findById(request.getId());

        if (optionalUser.isEmpty()) {
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        } else {

            User savedUser = optionalUser.get();
            savedUser.setFirstName(request.getFirstName());
            savedUser.setMiddleName(request.getMiddleName());
            savedUser.setLastName(request.getLastName());
            savedUser.setTelephoneNumber(request.getTelephoneNumber());
            savedUser.setUserType(request.getUserType());
            savedUser.setGender(request.getGender());

            userRepository.save(savedUser);

            String message = "Thank you for updating account.\n";
            rabbitMqProducer.sendNotification(new NotificationRequest(message, NotificationType.MAIL, savedUser.getEmail()));

            return UserConverter.toResponse(savedUser);
        }

    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));

        return UserConverter.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        log.info(LogMessages.GET_ALL_USERS);
        kafkaProducer.sendLog(LogMessages.GET_ALL_USERS);
        List<User> users = userRepository.findAll();
        return UserConverter.toResponse(users);
    }

    public Set<Role> getUserRoles(String email) {
        log.info(LogMessages.GET_USER_ROLES);
        kafkaProducer.sendLog(LogMessages.GET_USER_ROLES);
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));
        Set<Role> roles = foundUser.getRoles();
        log.info("{} kullanıcısının {} rolleri getirildi", email, roles);
        kafkaProducer.sendLog(email + " kullanıcısının " + roles + " rolleri getirildi");
        return roles;
    }

    public UserResponse addUserRoles(String email, List<RoleType> roleTypeList) {
        log.info(LogMessages.ADD_USER_ROLES);
        kafkaProducer.sendLog(LogMessages.ADD_USER_ROLES);
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));
        Set<Role> roles = foundUser.getRoles();
        for (RoleType roleType : roleTypeList) {
            Role roleUser = roleRepository.findByRoleName(roleType).orElseThrow(() -> new RuntimeException("role bulunamadı"));
            roles.add(roleUser);
        }
        foundUser.setRoles(roles);
        userRepository.save(foundUser);
        log.info("{} kullanıcısına {} rolleri eklendi", email, roleTypeList);
        kafkaProducer.sendLog(email + " kullanıcısına " + roleTypeList + " rolleri eklendi");
        return UserConverter.toResponse(foundUser);
    }

    public UserResponse deleteUserRoles(String email, List<RoleType> roleTypeList) {
        log.info(LogMessages.DELETE_USER_ROLES);
        kafkaProducer.sendLog(LogMessages.DELETE_USER_ROLES);
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));
        Set<Role> roles = foundUser.getRoles();
        for (RoleType roleType : roleTypeList) {
            Role roleUser = roleRepository.findByRoleName(roleType).orElseThrow(() -> new RuntimeException("role bulunamadı"));
            roles.remove(roleUser);
        }
        foundUser.setRoles(roles);
        userRepository.save(foundUser);
        log.info("{} kullanıcısından {} rolleri silindi", email, roleTypeList);
        kafkaProducer.sendLog(email + " kullanıcısından " + roleTypeList + " rolleri silindi");
        return UserConverter.toResponse(foundUser);
    }

    public Map<String, User> getAllUsersMap() {
        return userRepository.findAll()
                .stream()
                .collect(Collectors.toMap(User::getEmail, Function.identity()));
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user bulunamadı"));

        return UserConverter.toResponse(user);
    }
}
