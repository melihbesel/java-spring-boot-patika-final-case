package com.patika.authservice.client.user;

import com.patika.authservice.client.user.dto.response.GenericResponse;
import com.patika.authservice.client.user.dto.response.UserVO;
import com.patika.authservice.dto.request.AuthLoginRequest;
import com.patika.authservice.dto.request.AuthRegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service", url = "localhost:8083/api/v1/users")
public interface UserClient {

    @GetMapping("/auth/email")
    GenericResponse<UserVO> getUserByEmailForAuth(@RequestParam String email);

    @PostMapping("/register")
    GenericResponse<UserVO> createUserForAuth(@RequestBody AuthRegisterRequest authRegisterRequest);

    @PostMapping("/login")
    GenericResponse<UserVO> validateUserForAuth(@RequestBody AuthLoginRequest authLoginRequest);
}
