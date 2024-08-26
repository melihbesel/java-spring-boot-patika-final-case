package com.patika.ticketservice.exception;

import com.patika.ticketservice.dto.response.GenericResponse;
import com.patika.ticketservice.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final KafkaProducer kafkaProducer;

    @ExceptionHandler(TicketException.class)
    public GenericResponse handleTicketException(TicketException ticketException){
        log.info("TicketException ExceptionHandler çağrıldı");
        kafkaProducer.sendException(ticketException.getMessage());
        return GenericResponse.failed(ticketException.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public GenericResponse handleException(RuntimeException exception){
        log.info("RuntimeException ExceptionHandler çağrıldı");
        kafkaProducer.sendException(exception.getMessage());
        return GenericResponse.failed(exception.getMessage());
    }

}
