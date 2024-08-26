package com.patika.ticketservice.dto.producer;

import com.patika.ticketservice.dto.producer.enums.NotificationType;
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
