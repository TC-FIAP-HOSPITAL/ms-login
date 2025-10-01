package com.fiap.ms.login.application.usecase.user.implementation;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserByIdUsecaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private GetUserByIdUsecaseImpl getUserByIdUsecase;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        user1 = new User(1L, "Test User 1", "test1@example.com", "testuser1", "password", Role.PACIENTE, now, now);
        user2 = new User(2L, "Test User 2", "test2@example.com", "testuser2", "password", Role.PACIENTE, now, now);
    }

    @Test
    void getUserById_sameUser_shouldReturnUser() {
        String userId = "1";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> result = getUserByIdUsecase.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user1.getId(), result.get().getId());
        assertEquals(user1.getName(), result.get().getName());
        assertEquals(user1.getEmail(), result.get().getEmail());
        assertEquals(user1.getUsername(), result.get().getUsername());
    }

    @Test
    void getUserById_adminGettingOtherUser_shouldReturnUser() {
        String userId = "2";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(true);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        Optional<User> result = getUserByIdUsecase.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user2.getId(), result.get().getId());
        assertEquals(user2.getName(), result.get().getName());
        assertEquals(user2.getEmail(), result.get().getEmail());
        assertEquals(user2.getUsername(), result.get().getUsername());
    }

    @Test
    void getUserById_nonAdminGettingOtherUser_shouldThrowAccessDeniedException() {
        String userId = "2";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> getUserByIdUsecase.getUserById(userId));
    }

    @Test
    void getUserById_userNotFound_shouldReturnEmptyOptional() {
        String userId = "1";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = getUserByIdUsecase.getUserById(userId);

        assertFalse(result.isPresent());
    }
}
