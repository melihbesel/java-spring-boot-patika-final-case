package com.patika.paymentservice.service;

import com.patika.paymentservice.converter.PaymentConverter;
import com.patika.paymentservice.dto.request.PaymentRequest;
import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.exception.PaymentException;
import com.patika.paymentservice.exception.ExceptionMessages;
import com.patika.paymentservice.model.enums.PaymentStatus;
import com.patika.paymentservice.repository.PaymentRepository;
import com.patika.paymentservice.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse createPayment(PaymentRequest request) {

        if (request.getAmount() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.PAYMENT_AMOUNT_CAN_NOT_BE_EMPTY);
            throw new PaymentException(ExceptionMessages.PAYMENT_AMOUNT_CAN_NOT_BE_EMPTY);
        }

        if (request.getPaymentType() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.PAYMENT_TYPE_CAN_NOT_BE_EMPTY);
            throw new PaymentException(ExceptionMessages.PAYMENT_TYPE_CAN_NOT_BE_EMPTY);
        }

        if (request.getBookingId() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.PAYMENT_BOOKING_ID_CAN_NOT_BE_EMPTY);
            throw new PaymentException(ExceptionMessages.PAYMENT_BOOKING_ID_CAN_NOT_BE_EMPTY);
        }

        Payment payment = PaymentConverter.toEntity(request, PaymentStatus.PAID);

        paymentRepository.save(payment);

        return PaymentConverter.toResponse(payment);
    }

    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return PaymentConverter.toResponse(payments);
    }
}
