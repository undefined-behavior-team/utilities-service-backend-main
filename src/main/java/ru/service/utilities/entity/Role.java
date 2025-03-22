package ru.service.utilities.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {
    ADMIN,
    CLIENT,
    HOMEOWNER,
    UNKNOWN;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
