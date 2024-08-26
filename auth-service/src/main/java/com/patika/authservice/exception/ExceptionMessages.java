package com.patika.authservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String AUTH_REGISTER_FAILED = "kayıt başarısız, tekrar deneyin.";
    public static final String AUTH_LOGIN_FAILED = "giriş başarısız, tekrar deneyin.";
}
