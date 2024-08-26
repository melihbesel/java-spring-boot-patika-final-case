package com.patika.userservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSaveRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
