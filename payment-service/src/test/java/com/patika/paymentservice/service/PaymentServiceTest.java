package com.patika.paymentservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.patika.paymentservice.converter.PaymentConverter;
import com.patika.paymentservice.dto.request.PaymentRequest;
import com.patika.paymentservice.dto.response.PaymentResponse;
import com.patika.paymentservice.exception.PaymentException;
import com.patika.paymentservice.exception.ExceptionMessages;
import com.patika.paymentservice.model.Payment;
import com.patika.paymentservice.model.enums.PaymentStatus;
import com.patika.paymentservice.model.enums.PaymentType;
import com.patika.paymentservice.repository.PaymentRepository;

@ExtendWith(SpringExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    void it_should_throw_payment_exception_when_amount_is_null() {
        // given
        PaymentRequest request = new PaymentRequest(1L, null, PaymentType.CREDIT_CARD);

        // when
        Throwable exception = catchThrowable(() -> paymentService.createPayment(request));

        // then
        assertThat(exception).isInstanceOf(PaymentException.class);
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.PAYMENT_AMOUNT_CAN_NOT_BE_EMPTY);

        verifyNoInteractions(paymentRepository);
    }

    @Test
    void it_should_throw_payment_exception_when_payment_type_is_null() {
        // given
        PaymentRequest request = new PaymentRequest(1L, BigDecimal.TEN, null);

        // when
        Throwable exception = catchThrowable(() -> paymentService.createPayment(request));

        // then
        assertThat(exception).isInstanceOf(PaymentException.class);
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.PAYMENT_TYPE_CAN_NOT_BE_EMPTY);

        verifyNoInteractions(paymentRepository);
    }

    @Test
    void it_should_throw_payment_exception_when_booking_id_is_null() {
        // given
        PaymentRequest request = new PaymentRequest(null, BigDecimal.TEN, PaymentType.CREDIT_CARD);

        // when
        Throwable exception = catchThrowable(() -> paymentService.createPayment(request));

        // then
        assertThat(exception).isInstanceOf(PaymentException.class);
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.PAYMENT_BOOKING_ID_CAN_NOT_BE_EMPTY);

        verifyNoInteractions(paymentRepository);
    }

    @Test
    void it_should_create_payment_successfully() {
        // given
        PaymentRequest request = new PaymentRequest(1L, BigDecimal.TEN, PaymentType.CREDIT_CARD);
        Payment payment = new Payment(1L, BigDecimal.TEN, PaymentType.CREDIT_CARD, LocalDateTime.now(), PaymentStatus.PAID);

        when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment);

        // when
        PaymentResponse response = paymentService.createPayment(request);

        // then
        PaymentResponse expectedResponse = PaymentConverter.toResponse(payment);
        assertThat(response.getBookingId()).isEqualTo(expectedResponse.getBookingId());
        assertThat(response.getAmount()).isEqualTo(expectedResponse.getAmount());
        assertThat(response.getPaymentType()).isEqualTo(expectedResponse.getPaymentType());
        assertThat(response.getPaymentStatus()).isEqualTo(expectedResponse.getPaymentStatus());

        verify(paymentRepository, times(1)).save(Mockito.any(Payment.class));
    }

    @Test
    void it_should_return_all_payments() {
        // given
        Payment payment1 = new Payment(1L, BigDecimal.TEN, PaymentType.CREDIT_CARD, LocalDateTime.now(), PaymentStatus.PAID);
        Payment payment2 = new Payment(2L, BigDecimal.valueOf(20), PaymentType.EFT, LocalDateTime.now(), PaymentStatus.PAID);
        List<Payment> payments = List.of(payment1, payment2);

        when(paymentRepository.findAll()).thenReturn(payments);

        // when
        List<PaymentResponse> responses = paymentService.getAllPayments();

        // then
        List<PaymentResponse> expectedResponses = PaymentConverter.toResponse(payments);
        assertThat(responses).hasSize(expectedResponses.size());
        for (int i = 0; i < responses.size(); i++) {
            PaymentResponse response = responses.get(i);
            PaymentResponse expectedResponse = expectedResponses.get(i);
            assertThat(response.getBookingId()).isEqualTo(expectedResponse.getBookingId());
            assertThat(response.getAmount()).isEqualTo(expectedResponse.getAmount());
            assertThat(response.getPaymentType()).isEqualTo(expectedResponse.getPaymentType());
            assertThat(response.getPaymentStatus()).isEqualTo(expectedResponse.getPaymentStatus());
        }

        verify(paymentRepository, times(1)).findAll();
    }
}
