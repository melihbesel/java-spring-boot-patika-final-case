package com.patika.ticketservice.producer;

import com.patika.ticketservice.producer.constant.KafkaTopicConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public final class KafkaProducer {

    private final KafkaTemplate<String, Object> stringkafkaTemplate;

    public void sendException(String message) {
        stringkafkaTemplate.send(KafkaTopicConstants.EXCEPTION_INDEX_TOPIC, message);
        log.info("exception kafka'ya gönderildi. topics:{}, message: {}", KafkaTopicConstants.EXCEPTION_INDEX_TOPIC, message);
    }

    public void sendLog(String message) {
        stringkafkaTemplate.send(KafkaTopicConstants.LOG_INDEX_TOPIC, message);
        log.info("log kafka'ya gönderildi. topics:{}, message: {}", KafkaTopicConstants.LOG_INDEX_TOPIC, message);
    }

}
