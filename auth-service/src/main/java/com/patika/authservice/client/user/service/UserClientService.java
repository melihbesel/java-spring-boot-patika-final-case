package com.patika.authservice.client.user.service;

import com.patika.authservice.client.user.UserClient;
import com.patika.authservice.client.user.dto.response.UserVO;
import com.patika.authservice.dto.request.AuthLoginRequest;
import com.patika.authservice.dto.request.AuthRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClientService {

    private final UserClient userClient;

    public UserVO getUserByEmailForAuth(String email){
        return userClient.getUserByEmailForAuth(email).getData();
    }

    public UserVO createUserForAuth(AuthRegisterRequest authRegisterRequest){
        return userClient.createUserForAuth(authRegisterRequest).getData();
    }

    public UserVO validateUserForAuth(AuthLoginRequest authLoginRequest){
        return userClient.validateUserForAuth(authLoginRequest).getData();
    }
}
