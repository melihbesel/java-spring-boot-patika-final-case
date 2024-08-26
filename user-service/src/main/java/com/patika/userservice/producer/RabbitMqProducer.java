package com.patika.userservice.producer;

import com.patika.userservice.config.RabbitMQConfig;
import com.patika.userservice.producer.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqProducer {

    private final AmqpTemplate rabbitTemplate;

    private final RabbitMQConfig rabbitMQConfig;

    public void sendNotification(NotificationRequest notificationRequest) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.getExchange(), rabbitMQConfig.getRoutingkey(), notificationRequest);

        log.info("notification kuyruğa gönderildi. kuyruk:{}, message: {}", rabbitMQConfig.getQueueName(), notificationRequest);

    }

}
