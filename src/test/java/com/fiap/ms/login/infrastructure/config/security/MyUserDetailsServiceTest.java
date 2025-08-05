package com.fiap.ms.login.infrastructure.config.security;

import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
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
class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        String username = "testuser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password123");
        user.setRole(Role.USER);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertNotNull(result);
        assertTrue(result instanceof MyUserDetails);
        MyUserDetails myUserDetails = (MyUserDetails) result;
        assertEquals(1L, myUserDetails.getUserId());
        assertEquals(username, myUserDetails.getUsername());
        assertEquals("password123", myUserDetails.getPassword());
        assertTrue(myUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        
        verify(userRepository).findByUsername(username);
    }

    @Test
    void loadUserByUsername_shouldReturnAdminUserDetails_whenAdminUserExists() {
        String username = "admin";
        User user = new User();
        user.setId(2L);
        user.setUsername(username);
        user.setPassword("adminpass");
        user.setRole(Role.ADMIN);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertNotNull(result);
        assertTrue(result instanceof MyUserDetails);
        MyUserDetails myUserDetails = (MyUserDetails) result;
        assertEquals(2L, myUserDetails.getUserId());
        assertEquals(username, myUserDetails.getUsername());
        assertEquals("adminpass", myUserDetails.getPassword());
        assertTrue(myUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        
        verify(userRepository).findByUsername(username);
    }

    @Test
    void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserNotFound() {
        String username = "nonexistent";
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
            UsernameNotFoundException.class,
            () -> userDetailsService.loadUserByUsername(username)
        );

        assertEquals("User not found.", exception.getMessage());
        verify(userRepository).findByUsername(username);
    }
}