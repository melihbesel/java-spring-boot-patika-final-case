package com.patika.authservice.dto.request;

import com.patika.authservice.dto.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserType userType;
}
