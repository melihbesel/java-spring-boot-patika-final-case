package com.patika.paymentservice.exception;

import com.patika.paymentservice.dto.response.GenericResponse;
import com.patika.paymentservice.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final KafkaProducer kafkaProducer;

    @ExceptionHandler(PaymentException.class)
    public GenericResponse handlePaymentException(PaymentException paymentException){
        log.info("PaymentException ExceptionHandler çağrıldı");
        kafkaProducer.sendException(paymentException.getMessage());
        return GenericResponse.failed(paymentException.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public GenericResponse handleException(RuntimeException exception){
        log.info("RuntimeException ExceptionHandler çağrıldı");
        kafkaProducer.sendException(exception.getMessage());
        return GenericResponse.failed(exception.getMessage());
    }

}
