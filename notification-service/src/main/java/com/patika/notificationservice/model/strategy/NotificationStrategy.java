package com.patika.notificationservice.model.strategy;

import com.solmaz.notificationservice.model.Notification;

public interface NotificationStrategy {
    void sendNotification(Notification notification);
}
