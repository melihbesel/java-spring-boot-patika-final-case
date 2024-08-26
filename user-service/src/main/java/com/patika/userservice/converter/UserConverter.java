package com.patika.userservice.converter;

import com.patika.userservice.dto.response.UserResponse;
import com.patika.userservice.dto.response.UserVO;
import com.patika.userservice.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .receiverId(user.getReceiverId())
                .telephoneNumber(user.getTelephoneNumber())
                .userType(user.getUserType())
                .build();
    }

    public static List<UserResponse> toResponse(List<User> users) {
        return users.stream().map(UserConverter::toResponse).toList();
    }

    public static UserVO convertToResponseUserVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
