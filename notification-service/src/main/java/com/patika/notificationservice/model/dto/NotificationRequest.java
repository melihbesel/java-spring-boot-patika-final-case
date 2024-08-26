package com.patika.notificationservice.model.dto;

import com.solmaz.notificationservice.model.enums.NotificationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationRequest {
    private String message;
    private NotificationType notificationType;
    private String contact; //telephoneNumber, receiverId, email
}
