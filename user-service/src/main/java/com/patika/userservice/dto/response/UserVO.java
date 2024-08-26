package com.patika.userservice.dto.response;

import com.patika.userservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserVO {
    private Long id;
    private String email;
    private String password;
    private Set<Role> roles;
}
