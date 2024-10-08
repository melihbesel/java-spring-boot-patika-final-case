package com.patika.notificationservice.listener;

import com.patika.notificationservice.model.Notification;
import com.patika.notificationservice.model.dto.NotificationRequest;
import com.patika.notificationservice.model.enums.NotificationType;

import com.patika.notificationservice.model.strategy.EmailNotificationStrategy;
import com.patika.notificationservice.model.strategy.PushNotificationStrategy;
import com.patika.notificationservice.model.strategy.SmsNotificationStrategy;
import com.patika.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationListener {

    private final SmsNotificationStrategy smsNotificationStrategy;
    private final EmailNotificationStrategy emailNotificationStrategy;
    private final PushNotificationStrategy pushNotificationStrategy;

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.notification.queue}")
    public void notificationListener(NotificationRequest notificationRequest) {

        log.info("Received notification request: {}", notificationRequest);

        NotificationType notificationType = notificationRequest.getNotificationType();
        String contact = notificationRequest.getContact();
        String message = notificationRequest.getMessage();

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setContact(contact);

        if (notificationType.equals(NotificationType.SMS)) {
            notificationService.sendNotification(smsNotificationStrategy, notification);
        }
        if (notificationType.equals(NotificationType.PUSH)) {
            notificationService.sendNotification(pushNotificationStrategy, notification);
        }
        if (notificationType.equals(NotificationType.MAIL)) {
            notificationService.sendNotification(emailNotificationStrategy, notification);
        }

    }
}
