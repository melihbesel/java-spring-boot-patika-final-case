package com.patika.indexservice.consumer.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaTopicConstants {
    public static final String EXCEPTION_INDEX_TOPIC = "exception_index_topic";
    public static final String LOG_INDEX_TOPIC = "log_index_topic";
}
