package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserDomainUsecaseImplTest {

    @Mock
    private User user;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private UpdateUserUsecaseImpl updateUserUsecase;

    private UserDomain userDomain1;
    private UserDomain userDomain2;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        userDomain1 = new UserDomain(1L, "Test User 1", "test1@example.com", "testuser1", "password", RoleEnum.PACIENTE, now, now);
        userDomain2 = new UserDomain(2L, "Test User 2", "test2@example.com", "testuser2", "password", RoleEnum.PACIENTE, now, now);
    }

    @Test
    void updateUser_sameUser_shouldUpdateAndReturnUser() {
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(false);
        when(securityUtil.getRole()).thenReturn(RoleEnum.PACIENTE);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(user.update(any(UserDomain.class))).thenReturn(userDomain1);

        UserDomain result = updateUserUsecase.updateUser(userDomain1);

        assertNotNull(result);
        assertEquals(userDomain1.getId(), result.getId());
        assertEquals(userDomain1.getName(), result.getName());
        assertEquals(userDomain1.getEmail(), result.getEmail());
        assertEquals(userDomain1.getUsername(), result.getUsername());

        verify(passwordEncoder).encode("password");
        verify(user).update(userDomain1);
    }

    @Test
    void updateUser_adminUpdatingOtherUser_shouldUpdateAndReturnUser() {
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(user.update(any(UserDomain.class))).thenReturn(userDomain2);

        UserDomain result = updateUserUsecase.updateUser(userDomain2);

        assertNotNull(result);
        assertEquals(userDomain2.getId(), result.getId());
        assertEquals(userDomain2.getName(), result.getName());
        assertEquals(userDomain2.getEmail(), result.getEmail());
        assertEquals(userDomain2.getUsername(), result.getUsername());

        verify(passwordEncoder).encode("password");
        verify(user).update(userDomain2);
    }

    @Test
    void updateUser_nonAdminUpdatingOtherUser_shouldThrowAccessDeniedException() {
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> updateUserUsecase.updateUser(userDomain2));
    }
}
