package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.application.gateways.User;
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
class GetUserDomainByIdUsecaseImplTest {

    @Mock
    private User user;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private GetUserByIdUsecaseImpl getUserByIdUsecase;

    private UserDomain userDomain1;
    private UserDomain userDomain2;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        userDomain1 = new UserDomain(1L, "Test User 1", "test1@example.com", "testuser1", "password", RoleEnum.PACIENTE, now, now);
        userDomain2 = new UserDomain(2L, "Test User 2", "test2@example.com", "testuser2", "password", RoleEnum.PACIENTE, now, now);
    }

    @Test
    void getUserById_sameUser_shouldReturnUser() {
        String userId = "1";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(user.findById(1L)).thenReturn(Optional.of(userDomain1));

        Optional<UserDomain> result = getUserByIdUsecase.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userDomain1.getId(), result.get().getId());
        assertEquals(userDomain1.getName(), result.get().getName());
        assertEquals(userDomain1.getEmail(), result.get().getEmail());
        assertEquals(userDomain1.getUsername(), result.get().getUsername());
    }

    @Test
    void getUserById_adminGettingOtherUser_shouldReturnUser() {
        String userId = "2";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(true);
        when(user.findById(2L)).thenReturn(Optional.of(userDomain2));

        Optional<UserDomain> result = getUserByIdUsecase.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userDomain2.getId(), result.get().getId());
        assertEquals(userDomain2.getName(), result.get().getName());
        assertEquals(userDomain2.getEmail(), result.get().getEmail());
        assertEquals(userDomain2.getUsername(), result.get().getUsername());
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
        when(user.findById(1L)).thenReturn(Optional.empty());

        Optional<UserDomain> result = getUserByIdUsecase.getUserById(userId);

        assertFalse(result.isPresent());
    }
}
