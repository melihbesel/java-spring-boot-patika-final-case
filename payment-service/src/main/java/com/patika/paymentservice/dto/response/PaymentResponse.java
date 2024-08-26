package com.patika.paymentservice.dto.response;

import com.patika.paymentservice.model.enums.PaymentStatus;
import com.patika.paymentservice.model.enums.PaymentType;
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
