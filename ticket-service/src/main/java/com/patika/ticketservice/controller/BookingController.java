package com.patika.ticketservice.controller;

import com.patika.ticketservice.client.payment.service.dto.request.PaymentRequest;
import com.patika.ticketservice.client.payment.service.dto.response.PaymentResponse;
import com.patika.ticketservice.dto.request.BookingRequest;
import com.patika.ticketservice.dto.response.BookingResponse;
import com.patika.ticketservice.dto.response.GenericResponse;
import com.patika.ticketservice.service.BookingService;
import com.patika.ticketservice.util.LoggerHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public GenericResponse<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingController --> createBooking()--> bookingRequest has been sent to BookingService createBooking()");

        return GenericResponse.success(bookingService.createBooking(bookingRequest), HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public GenericResponse<BookingResponse> getBookingById(@PathVariable Long bookingId) {
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingController --> getBookingById()--> booking ID has been sent to BookingService getBookingById()");

        return GenericResponse.success(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }

    @PostMapping("/payment")
    public GenericResponse<PaymentResponse> payment(@RequestBody PaymentRequest paymentRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingController --> getBookingById()--> paymentRequest has been sent to BookingService payment()");

        return GenericResponse.success(bookingService.payment(paymentRequest), HttpStatus.OK);
    }

}
