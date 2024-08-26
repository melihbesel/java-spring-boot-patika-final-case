package com.patika.ticketservice.client.payment;

import com.patika.ticketservice.client.payment.service.dto.request.PaymentRequest;
import com.patika.ticketservice.client.payment.service.dto.response.PaymentResponse;
import com.patika.ticketservice.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment-service", url = "localhost:8082/api/v1/payments")
public interface PaymentClient {

    @PostMapping
    GenericResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest request);
}
