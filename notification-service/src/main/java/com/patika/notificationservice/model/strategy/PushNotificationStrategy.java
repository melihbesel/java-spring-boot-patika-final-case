package com.patika.notificationservice.model.strategy;
import com.patika.notificationservice.model.Notification;
import com.patika.notificationservice.model.enums.NotificationType;
import com.patika.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class PushNotificationStrategy implements NotificationStrategy {

    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(Notification notification) {
        notification.setNotificationType(NotificationType.PUSH);
        notificationRepository.save(notification);
        log.info("Push notification recorded: " + notification.getMessage());
    }
}
