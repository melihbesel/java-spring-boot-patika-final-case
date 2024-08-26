package com.patika.authservice.exception;

import com.patika.authservice.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public GenericResponse handleAuthException(AuthException authException){
        log.info("AuthException ExceptionHandler çağrıldı");
        return GenericResponse.failed(authException.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public GenericResponse handleException(RuntimeException exception){
        log.info("RuntimeException ExceptionHandler çağrıldı");
        return GenericResponse.failed(exception.getMessage());
    }

}
