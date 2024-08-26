package com.patika.ticketservice.client.user.service;

import com.patika.ticketservice.client.user.UserClient;
import com.patika.ticketservice.client.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClientService {

    private final UserClient userClient;

    public UserResponse getUserByEmail(String email){
        return userClient.getUserByEmail(email).getData();
    }

    public UserResponse getUserById(Long userId) {
        return userClient.getUserById(userId).getData();
    }
}
