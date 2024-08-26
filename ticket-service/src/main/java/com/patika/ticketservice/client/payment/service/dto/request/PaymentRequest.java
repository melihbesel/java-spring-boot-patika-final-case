package com.patika.ticketservice.client.payment.service.dto.request;

import com.patika.ticketservice.client.payment.service.dto.response.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private Long bookingId;
    private BigDecimal amount;
    private PaymentType paymentType;

}
