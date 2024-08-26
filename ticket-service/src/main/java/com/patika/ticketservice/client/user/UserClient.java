package com.patika.ticketservice.client.user;

import com.patika.ticketservice.client.user.dto.response.UserResponse;
import com.patika.ticketservice.dto.response.GenericResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service", url = "localhost:8083/api/v1/users")
public interface UserClient {

    @GetMapping("/email")
    GenericResponse<UserResponse> getUserByEmail(@RequestParam String email);

    @GetMapping("/id")
    GenericResponse<UserResponse> getUserById(@RequestParam Long id);
}
