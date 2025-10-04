package com.fiap.ms.login.infrastructure.config.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.domain.model.UserDomain;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final User user;

    public MyUserDetailsService(User user) {
        this.user = user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDomain userDomain = user.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return MyUserDetails.builder()
                .userId(userDomain.getId())
                .username(userDomain.getUsername())
                .password(userDomain.getPassword())
                .email(userDomain.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + userDomain.getRole().name())))
                .build();
    }
}
