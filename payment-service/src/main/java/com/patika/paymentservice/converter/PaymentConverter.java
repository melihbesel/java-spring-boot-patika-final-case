package com.patika.paymentservice.converter;

import com.patika.paymentservice.dto.request.PaymentRequest;
import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.model.Payment;
import com.patika.paymentservice.model.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentConverter {


    public static Payment toEntity(PaymentRequest request, PaymentStatus paymentStatus) {
        return new Payment(
                request.getBookingId(),
                request.getAmount(),
                request.getPaymentType(),
                LocalDateTime.now(),
                paymentStatus
        );
    }

    public static PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .paymentType(payment.getPaymentType())
                .createdDateTime(LocalDateTime.now())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

    public static List<PaymentResponse> toResponse(List<Payment> payments) {
        return payments.stream()
                .map(PaymentConverter::toResponse)
                .toList();
    }
}
