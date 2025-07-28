package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoderGateway;
import com.fiap.ms.login.domain.model.Address;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUsecaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderGateway passwordEncoderGateway;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private UpdateUserUsecaseImpl updateUserUsecase;

    private User user1;
    private User user2;
    private Address address1;
    private Address address2;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        address1 = new Address(1L, "Test Street", "123", "Apt 4", "Test City", "TS");
        user1 = new User(1L, "Test User 1", "test1@example.com", "testuser1", "password", Role.USER, now, now, address1);

        address2 = new Address(2L, "Other Street", "456", "Apt 7", "Other City", "OS");
        user2 = new User(2L, "Test User 2", "test2@example.com", "testuser2", "password", Role.USER, now, now, address2);
    }

    @Test
    void updateUser_sameUser_shouldUpdateAndReturnUser() {
        when(securityUtil.getUserId()).thenReturn(1L);
        when(passwordEncoderGateway.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.update(any(User.class))).thenReturn(user1);

        User result = updateUserUsecase.updateUser(user1);

        assertNotNull(result);
        assertEquals(user1.getId(), result.getId());
        assertEquals(user1.getName(), result.getName());
        assertEquals(user1.getEmail(), result.getEmail());
        assertEquals(user1.getUsername(), result.getUsername());

        verify(passwordEncoderGateway).encode("password");
        verify(userRepository).update(user1);
    }

    @Test
    void updateUser_adminUpdatingOtherUser_shouldUpdateAndReturnUser() {
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(true);
        when(passwordEncoderGateway.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.update(any(User.class))).thenReturn(user2);

        User result = updateUserUsecase.updateUser(user2);

        assertNotNull(result);
        assertEquals(user2.getId(), result.getId());
        assertEquals(user2.getName(), result.getName());
        assertEquals(user2.getEmail(), result.getEmail());
        assertEquals(user2.getUsername(), result.getUsername());

        verify(passwordEncoderGateway).encode("password");
        verify(userRepository).update(user2);
    }

    @Test
    void updateUser_nonAdminUpdatingOtherUser_shouldThrowAccessDeniedException() {
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> updateUserUsecase.updateUser(user2));
    }
}
