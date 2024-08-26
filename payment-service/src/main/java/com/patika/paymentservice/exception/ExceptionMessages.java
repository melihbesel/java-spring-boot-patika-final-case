package com.patika.paymentservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String PAYMENT_AMOUNT_CAN_NOT_BE_EMPTY = "ödeme miktarı boş gelemez";
    public static final String PAYMENT_BOOKING_ID_CAN_NOT_BE_EMPTY = "booking id boş olamaz";
    public static final String PAYMENT_TYPE_CAN_NOT_BE_EMPTY = "ödeme türü boş olamaz";
}
