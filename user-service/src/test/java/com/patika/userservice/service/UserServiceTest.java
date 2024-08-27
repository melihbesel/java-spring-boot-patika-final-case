package com.patika.userservice.service;

import com.patika.userservice.dto.request.AuthLoginRequest;
import com.patika.userservice.dto.request.AuthRegisterRequest;
import com.patika.userservice.dto.request.UserRequest;
import com.patika.userservice.dto.response.UserResponse;
import com.patika.userservice.dto.response.UserVO;
import com.patika.userservice.model.Role;
import com.patika.userservice.model.User;
import com.patika.userservice.model.enums.RoleType;
import com.patika.userservice.model.enums.UserType;
import com.patika.userservice.producer.KafkaProducer;
import com.patika.userservice.producer.RabbitMqProducer;
import com.patika.userservice.producer.dto.NotificationRequest;
import com.patika.userservice.repository.RoleRepository;
import com.patika.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RabbitMqProducer rabbitMqProducer;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private Role userRole;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("mbesel2005@gmail.com");
        testUser.setPassword("encodedPassword");
        testUser.setUserType(UserType.INDIVIDUAL);

        userRole = new Role();
        userRole.setId(1L);
        userRole.setRoleName(RoleType.USER);

        adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setRoleName(RoleType.ADMIN);
    }

    @Test
    void testGetUserByEmailForAuth() {
        when(userRepository.findByEmail("mbesel2005@gmail.com")).thenReturn(Optional.of(testUser));
        UserVO result = userService.getUserByEmailForAuth("mbesel2005@gmail.com");
        assertNotNull(result);
        assertEquals("mbesel2005@gmail.com", result.getEmail());
    }

    @Test
    void testCreateUserForAuth() {
        AuthRegisterRequest request = AuthRegisterRequest.builder()
                .firstName("Melih")
                .lastName("Beşel")
                .email("mbesel2005@gmail.com")
                .password("password")
                .userType(UserType.INDIVIDUAL)
                .build();

        when(userRepository.findByEmail("mbesel2005@gmail.com")).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName(RoleType.USER)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        UserVO result = userService.createUserForAuth(request);
        assertNotNull(result);
        assertEquals("mbesel2005@gmail.com", result.getEmail());
        verify(rabbitMqProducer).sendNotification(any(NotificationRequest.class));
    }

    @Test
    void testValidateUserForAuth() {
        AuthLoginRequest request = AuthLoginRequest.builder()
                .email("mbesel2005@gmail.com")
                .password("password")
                .build();

        when(userRepository.findByEmail("mbesel2005@gmail.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        UserVO result = userService.validateUserForAuth(request);
        assertNotNull(result);
        assertEquals("mbesel2005@gmail.com", result.getEmail());
    }

    @Test
    void testUpdateUser() {
        UserRequest request = UserRequest.builder()
                .id(1L)
                .firstName("Melih")
                .middleName("")
                .lastName("Beşel")
                .telephoneNumber("123456789")
                .userType(UserType.CORPORATE)
                .gender(com.patika.userservice.model.enums.Gender.MALE)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserResponse result = userService.updateUser(request);
        assertNotNull(result);
        assertEquals(UserType.CORPORATE, result.getUserType());
        verify(rabbitMqProducer).sendNotification(any(NotificationRequest.class));
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail("mbesel2005@gmail.com")).thenReturn(Optional.of(testUser));
        UserResponse result = userService.getUserByEmail("mbesel2005@gmail.com");
        assertNotNull(result);
        assertEquals("mbesel2005@gmail.com", result.getEmail());
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = Collections.singletonList(testUser);
        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponse> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testAddUserRoles() {
        Set<Role> existingRoles = new HashSet<>(Collections.singletonList(userRole));
        testUser.setRoles(existingRoles);

        List<RoleType> rolesToAdd = Collections.singletonList(RoleType.ADMIN);
        when(userRepository.findByEmail("mbesel2005@gmail.com")).thenReturn(Optional.of(testUser));
        when(roleRepository.findByRoleName(RoleType.ADMIN)).thenReturn(Optional.of(adminRole));

        UserResponse result = userService.addUserRoles("mbesel2005@gmail.com", rolesToAdd);
        assertNotNull(result);
        assertEquals(UserType.INDIVIDUAL, result.getUserType());
        verify(kafkaProducer, times(2)).sendLog(anyString());
    }

    @Test
    void testDeleteUserRoles() {
        Set<Role> existingRoles = new HashSet<>(Collections.singletonList(userRole));
        testUser.setRoles(existingRoles);

        List<RoleType> rolesToDelete = Collections.singletonList(RoleType.USER);
        Role roleToDelete = new Role();
        roleToDelete.setId(1L);
        roleToDelete.setRoleName(RoleType.USER);

        when(userRepository.findByEmail("mbesel2005@gmail.com")).thenReturn(Optional.of(testUser));
        when(roleRepository.findByRoleName(RoleType.USER)).thenReturn(Optional.of(roleToDelete));

        UserResponse result = userService.deleteUserRoles("mbesel2005@gmail.com", rolesToDelete);
        assertNotNull(result);
        assertEquals(UserType.INDIVIDUAL, result.getUserType());
        verify(kafkaProducer, times(2)).sendLog(anyString());
    }


    @Test
    void testGetAllUsersMap() {
        List<User> users = Collections.singletonList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        Map<String, User> result = userService.getAllUsersMap();
        assertNotNull(result);
        assertTrue(result.containsKey("mbesel2005@gmail.com"));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        UserResponse result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void testGetUserRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, RoleType.USER));
        roles.add(new Role(2L, RoleType.ADMIN));

        User testUser = new User();
        testUser.setEmail("mbesel2005@gmail.com");
        testUser.setRoles(roles);

        when(userRepository.findByEmail("mbesel2005@gmail.com")).thenReturn(Optional.of(testUser));

        Set<Role> resultRoles = userService.getUserRoles("mbesel2005@gmail.com");

        assertNotNull(resultRoles);
        assertEquals(2, resultRoles.size());
        assertTrue(resultRoles.stream().anyMatch(role -> role.getRoleName() == RoleType.USER));
        assertTrue(resultRoles.stream().anyMatch(role -> role.getRoleName() == RoleType.ADMIN));

        verify(kafkaProducer, times(2)).sendLog(anyString());
    }

}
