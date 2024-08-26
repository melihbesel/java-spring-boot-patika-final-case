package com.patika.notificationservice.model.strategy;

import com.solmaz.notificationservice.model.Notification;
import com.solmaz.notificationservice.model.enums.NotificationType;
import com.solmaz.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class SmsNotificationStrategy implements NotificationStrategy {

    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(Notification notification) {
        notification.setNotificationType(NotificationType.SMS);
        notificationRepository.save(notification);
        log.info("Sms notification recorded: " + notification.getMessage());
    }
}
