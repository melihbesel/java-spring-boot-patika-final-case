package com.patika.indexservice.consumer;

import com.patika.indexservice.consumer.constants.KafkaTopicConstants;
import com.patika.indexservice.service.ExceptionService;
import com.patika.indexservice.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ExceptionService exceptionService;
    private final LogService logService;

    @KafkaListener(topics = KafkaTopicConstants.EXCEPTION_INDEX_TOPIC, groupId = "${kafka.group-id}")
    public void listenException(String message) {
        log.info("listenException çağrıldı");
        log.info("Received Message: {}", message);
        exceptionService.saveException(message);
    }

    @KafkaListener(topics = KafkaTopicConstants.LOG_INDEX_TOPIC, groupId = "${kafka.group-id}")
    public void listenLog(String message) {
        log.info("listenLog çağrıldı");
        log.info("Received Message: {}", message);
        logService.saveLog(message);
    }

}
