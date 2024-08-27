package com.patika.notificationservice.service;

import com.patika.notificationservice.model.Notification;
import com.patika.notificationservice.model.strategy.NotificationStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationStrategy emailNotificationStrategy;

    @Mock
    private NotificationStrategy pushNotificationStrategy;

    @Mock
    private NotificationStrategy smsNotificationStrategy;

    @Test
    void it_should_send_email_notification() {
        // Given
        Notification notification = new Notification();
        notification.setMessage("Email notification message");

        // When
        notificationService.sendNotification(emailNotificationStrategy, notification);

        // Then
        verify(emailNotificationStrategy, times(1)).sendNotification(notification);
        verifyNoMoreInteractions(emailNotificationStrategy);
        verifyNoInteractions(pushNotificationStrategy, smsNotificationStrategy);
    }

    @Test
    void it_should_send_push_notification() {
        // Given
        Notification notification = new Notification();
        notification.setMessage("Push notification message");

        // When
        notificationService.sendNotification(pushNotificationStrategy, notification);

        // Then
        verify(pushNotificationStrategy, times(1)).sendNotification(notification);
        verifyNoMoreInteractions(pushNotificationStrategy);
        verifyNoInteractions(emailNotificationStrategy, smsNotificationStrategy);
    }

    @Test
    void it_should_send_sms_notification() {
        // Given
        Notification notification = new Notification();
        notification.setMessage("SMS notification message");

        // When
        notificationService.sendNotification(smsNotificationStrategy, notification);

        // Then
        verify(smsNotificationStrategy, times(1)).sendNotification(notification);
        verifyNoMoreInteractions(smsNotificationStrategy);
        verifyNoInteractions(emailNotificationStrategy, pushNotificationStrategy);
    }
}
