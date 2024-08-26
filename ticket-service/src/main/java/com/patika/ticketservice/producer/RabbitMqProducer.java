package com.patika.ticketservice.producer;

import com.patika.ticketservice.config.RabbitMQProducerConfig;
import com.patika.ticketservice.dto.producer.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqProducer {

    private final AmqpTemplate rabbitTemplate;

    private final RabbitMQProducerConfig rabbitMQProducerConfig;

    public void sendNotification(NotificationRequest notificationRequest) {
        rabbitTemplate.convertAndSend(rabbitMQProducerConfig.getExchange(), rabbitMQProducerConfig.getRoutingkey(), notificationRequest);

        log.info("notification kuyruğa gönderildi. kuyruk:{}, ticket: {}", rabbitMQProducerConfig.getQueueName(), notificationRequest);

    }

}
