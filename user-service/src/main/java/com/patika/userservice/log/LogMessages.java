package com.patika.userservice.log;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogMessages {

    public static final String GET_ALL_USERS = "getAllUsers methodu çağrıldı";
    public static final String GET_USER_ROLES = "getUserRoles methodu çağrıldı";
    public static final String ADD_USER_ROLES = "addUserRoles methodu çağrıldı";
    public static final String DELETE_USER_ROLES = "deleteUserRoles methodu çağrıldı";

}
