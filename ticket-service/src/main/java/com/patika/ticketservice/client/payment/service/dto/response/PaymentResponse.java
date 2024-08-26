package com.patika.ticketservice.client.payment.service.dto.response;

import com.patika.ticketservice.client.payment.service.dto.response.enums.PaymentType;
import com.patika.ticketservice.client.payment.service.dto.response.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponse {

    private Long bookingId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private LocalDateTime createdDateTime;
    private PaymentStatus paymentStatus;

}
