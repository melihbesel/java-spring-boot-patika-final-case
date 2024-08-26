package com.patika.authservice.services;

import com.patika.authservice.client.user.service.UserClientService;
import com.patika.authservice.dto.request.AuthLoginRequest;
import com.patika.authservice.dto.request.AuthRegisterRequest;
import com.patika.authservice.dto.response.AuthResponse;
import com.patika.authservice.client.user.dto.response.UserVO;
import com.patika.authservice.exception.AuthException;
import com.patika.authservice.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserClientService userClientService;
    private final JwtUtil jwt;

    public AuthResponse register(AuthRegisterRequest authRegisterRequest) {

        UserVO userVO = userClientService.createUserForAuth(authRegisterRequest);
        if (userVO == null) {
            throw new AuthException(ExceptionMessages.AUTH_REGISTER_FAILED);
        }

        String accessToken = jwt.generate(userVO, "ACCESS");
        String refreshToken = jwt.generate(userVO, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);

    }

    public AuthResponse login(AuthLoginRequest authLoginRequest) {

        UserVO userVO = userClientService.validateUserForAuth(authLoginRequest);
        if (userVO == null) {
            throw new AuthException(ExceptionMessages.AUTH_LOGIN_FAILED);
        }

        String accessToken = jwt.generate(userVO, "ACCESS");
        String refreshToken = jwt.generate(userVO, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);

    }
}
