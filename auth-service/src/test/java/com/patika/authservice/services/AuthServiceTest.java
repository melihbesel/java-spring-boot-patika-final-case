package com.patika.authservice.services;

import com.patika.authservice.client.user.service.UserClientService;
import com.patika.authservice.dto.enums.UserType;
import com.patika.authservice.dto.request.AuthLoginRequest;
import com.patika.authservice.dto.request.AuthRegisterRequest;
import com.patika.authservice.dto.response.AuthResponse;
import com.patika.authservice.client.user.dto.response.UserVO;
import com.patika.authservice.exception.AuthException;
import com.patika.authservice.exception.ExceptionMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void it_should_register_user_successfully() {
        // given
        AuthRegisterRequest registerRequest = new AuthRegisterRequest("test@example.com", "password", "John", "Doe", UserType.INDIVIDUAL);
        UserVO userVO = new UserVO(); // setup UserVO as needed
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        when(userClientService.createUserForAuth(registerRequest)).thenReturn(userVO);
        when(jwtUtil.generate(userVO, "ACCESS")).thenReturn(accessToken);
        when(jwtUtil.generate(userVO, "REFRESH")).thenReturn(refreshToken);

        // when
        AuthResponse response = authService.register(registerRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);

        verify(userClientService, times(1)).createUserForAuth(registerRequest);
        verify(jwtUtil, times(1)).generate(userVO, "ACCESS");
        verify(jwtUtil, times(1)).generate(userVO, "REFRESH");
    }

    @Test
    void it_should_fail_to_register_user_when_user_client_service_fails() {
        // given
        AuthRegisterRequest registerRequest = new AuthRegisterRequest("test@example.com", "password", "John", "Doe", UserType.INDIVIDUAL);

        when(userClientService.createUserForAuth(registerRequest)).thenReturn(null);

        // when
        Throwable thrown = catchThrowable(() -> authService.register(registerRequest));

        // then
        assertThat(thrown).isInstanceOf(AuthException.class);
        assertThat(thrown).hasMessage(ExceptionMessages.AUTH_REGISTER_FAILED);

        verify(userClientService, times(1)).createUserForAuth(registerRequest);
        verify(jwtUtil, times(0)).generate(any(UserVO.class), anyString());
    }

    @Test
    void it_should_login_user_successfully() {
        // given
        AuthLoginRequest loginRequest = new AuthLoginRequest("test@example.com", "password");
        UserVO userVO = new UserVO(); // setup UserVO as needed
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        when(userClientService.validateUserForAuth(loginRequest)).thenReturn(userVO);
        when(jwtUtil.generate(userVO, "ACCESS")).thenReturn(accessToken);
        when(jwtUtil.generate(userVO, "REFRESH")).thenReturn(refreshToken);

        // when
        AuthResponse response = authService.login(loginRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);

        verify(userClientService, times(1)).validateUserForAuth(loginRequest);
        verify(jwtUtil, times(1)).generate(userVO, "ACCESS");
        verify(jwtUtil, times(1)).generate(userVO, "REFRESH");
    }

    @Test
    void it_should_fail_to_login_user_when_user_client_service_fails() {
        // given
        AuthLoginRequest loginRequest = new AuthLoginRequest("test@example.com", "password");

        when(userClientService.validateUserForAuth(loginRequest)).thenReturn(null);

        // when
        Throwable thrown = catchThrowable(() -> authService.login(loginRequest));

        // then
        assertThat(thrown).isInstanceOf(AuthException.class);
        assertThat(thrown).hasMessage(ExceptionMessages.AUTH_LOGIN_FAILED);

        verify(userClientService, times(1)).validateUserForAuth(loginRequest);
        verify(jwtUtil, times(0)).generate(any(UserVO.class), anyString());
    }
}
