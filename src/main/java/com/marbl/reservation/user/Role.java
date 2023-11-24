package com.marbl.reservation.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.marbl.reservation.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_CREATE,
            ADMIN_DELETE,
            PARTNER_READ,
            PARTNER_UPDATE,
            PARTNER_CREATE,
            PARTNER_DELETE,
            USER_READ,
            USER_UPDATE,
            USER_CREATE,
            USER_DELETE
    )),
    PARTNER(Set.of(
            PARTNER_READ,
            PARTNER_UPDATE,
            PARTNER_CREATE,
            PARTNER_DELETE,
            USER_READ,
            USER_UPDATE,
            USER_CREATE,
            USER_DELETE
    )),
    USER(Set.of(
            USER_READ,
            USER_UPDATE,
            USER_CREATE,
            USER_DELETE
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toCollection(ArrayList::new));

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
