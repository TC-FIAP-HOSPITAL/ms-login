package com.fiap.ms.login.infrastructure.config.security;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.application.gateways.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyUserDomainDetailsServiceTest {

    @Mock
    private User user;

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        String username = "testuser";
        UserDomain userDomain = new UserDomain();
        userDomain.setId(1L);
        userDomain.setUsername(username);
        userDomain.setPassword("password123");
        userDomain.setRole(RoleEnum.PACIENTE);

        when(user.findByUsername(username)).thenReturn(Optional.of(userDomain));

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertNotNull(result);
        assertTrue(result instanceof MyUserDetails);
        MyUserDetails myUserDetails = (MyUserDetails) result;
        assertEquals(1L, myUserDetails.getUserId());
        assertEquals(username, myUserDetails.getUsername());
        assertEquals("password123", myUserDetails.getPassword());
        assertTrue(myUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PACIENTE")));
        
        verify(user).findByUsername(username);
    }

    @Test
    void loadUserByUsername_shouldReturnAdminUserDetails_whenAdminUserExists() {
        String username = "admin";
        UserDomain userDomain = new UserDomain();
        userDomain.setId(2L);
        userDomain.setUsername(username);
        userDomain.setPassword("adminpass");
        userDomain.setRole(RoleEnum.ADMIN);

        when(user.findByUsername(username)).thenReturn(Optional.of(userDomain));

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertNotNull(result);
        assertTrue(result instanceof MyUserDetails);
        MyUserDetails myUserDetails = (MyUserDetails) result;
        assertEquals(2L, myUserDetails.getUserId());
        assertEquals(username, myUserDetails.getUsername());
        assertEquals("adminpass", myUserDetails.getPassword());
        assertTrue(myUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        
        verify(user).findByUsername(username);
    }

    @Test
    void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserNotFound() {
        String username = "nonexistent";
        
        when(user.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
            UsernameNotFoundException.class,
            () -> userDetailsService.loadUserByUsername(username)
        );

        assertEquals("User not found.", exception.getMessage());
        verify(user).findByUsername(username);
    }
}