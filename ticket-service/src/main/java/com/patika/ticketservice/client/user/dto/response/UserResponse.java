package com.patika.ticketservice.client.user.dto.response;

import com.patika.ticketservice.client.user.dto.response.enums.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String email;
    private String telephoneNumber;
    private String receiverId;
    private UserType userType;
}
