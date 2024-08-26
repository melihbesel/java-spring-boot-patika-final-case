package com.patika.authservice.controllers;

import com.patika.authservice.dto.request.AuthLoginRequest;
import com.patika.authservice.dto.request.AuthRegisterRequest;
import com.patika.authservice.dto.response.AuthResponse;
import com.patika.authservice.dto.response.GenericResponse;
import com.patika.authservice.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public GenericResponse<AuthResponse> register(@RequestBody AuthRegisterRequest authRegisterRequest) {
        return GenericResponse.success(authService.register(authRegisterRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public GenericResponse<AuthResponse> login(@RequestBody AuthLoginRequest authLoginRequest) {
        return GenericResponse.success(authService.login(authLoginRequest), HttpStatus.OK);
    }

}
