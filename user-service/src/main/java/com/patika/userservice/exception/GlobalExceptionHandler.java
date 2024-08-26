package com.patika.userservice.exception;

import com.patika.userservice.dto.response.GenericResponse;
import com.patika.userservice.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final KafkaProducer kafkaProducer;

    @ExceptionHandler(UserException.class)
    public GenericResponse handleUserException(UserException userException){
        log.info("UserException ExceptionHandler çağrıldı");
        kafkaProducer.sendException(userException.getMessage());
        return GenericResponse.failed(userException.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public GenericResponse handleException(RuntimeException exception){
        log.info("RuntimeException ExceptionHandler çağrıldı");
        kafkaProducer.sendException(exception.getMessage());
        return GenericResponse.failed(exception.getMessage());
    }

}
