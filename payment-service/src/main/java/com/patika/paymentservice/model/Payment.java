package com.patika.paymentservice.model;

import com.patika.paymentservice.model.enums.PaymentType;
import com.patika.paymentservice.model.enums.PaymentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId; //email

    @Schema(name = "amount", description = "Ödeme miktarı", example = "9.99")
    @Column(name = "amount")
    private BigDecimal amount;

    //yeni
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "createdDateTime")
    private LocalDateTime createdDateTime;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Payment(Long bookingId, BigDecimal amount, PaymentType paymentType, LocalDateTime createdDateTime, PaymentStatus paymentStatus) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.createdDateTime = createdDateTime;
        this.paymentStatus = paymentStatus;
    }
}
