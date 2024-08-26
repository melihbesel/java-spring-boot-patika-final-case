package com.patika.notificationservice.service;

import com.patika.notificationservice.model.Notification;
import com.patika.notificationservice.model.strategy.NotificationStrategy;

import lombok.*;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

    public void sendNotification(NotificationStrategy notificationStrategy, Notification notification) {
        notificationStrategy.sendNotification(notification);
    }

}
