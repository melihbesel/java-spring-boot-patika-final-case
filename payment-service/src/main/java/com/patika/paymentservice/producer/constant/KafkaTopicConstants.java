package com.patika.paymentservice.producer.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaTopicConstants {
    public static final String EXCEPTION_INDEX_TOPIC = "exception_index_topic";
}
