package com.marbl.reservation.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {



    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    PARTNER_READ("partnership:read"),
    PARTNER_UPDATE("partnership:update"),
    PARTNER_CREATE("partnership:create"),
    PARTNER_DELETE("partnership:delete"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete");


    @Getter
    private final String permission;
}
