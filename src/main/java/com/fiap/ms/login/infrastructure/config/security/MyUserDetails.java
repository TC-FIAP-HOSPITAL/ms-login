package com.fiap.ms.login.infrastructure.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyUserDetails implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // or your own logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or your own logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // or your own logic
    }

    @Override
    public boolean isEnabled() {
        return true; // or your own logic
    }
}