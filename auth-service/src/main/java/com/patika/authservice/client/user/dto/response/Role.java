package com.patika.authservice.client.user.dto.response;

import com.patika.authservice.client.user.dto.response.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    private Long id;

    private RoleType roleName;

}
