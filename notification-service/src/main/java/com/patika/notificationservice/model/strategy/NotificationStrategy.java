package com.patika.notificationservice.model.strategy;

import com.patika.notificationservice.model.Notification;

public interface NotificationStrategy {
    void sendNotification(Notification notification);
}
