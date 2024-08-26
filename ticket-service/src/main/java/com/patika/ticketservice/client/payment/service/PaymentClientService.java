package com.patika.ticketservice.client.payment.service;

import com.patika.ticketservice.client.payment.PaymentClient;
import com.patika.ticketservice.client.payment.service.dto.request.PaymentRequest;
import com.patika.ticketservice.client.payment.service.dto.response.PaymentResponse;
import com.patika.ticketservice.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClientService {

    private final PaymentClient paymentClient;

    public PaymentResponse createPayment(PaymentRequest request) {
        return paymentClient.createPayment(request).getData();
    }
}
