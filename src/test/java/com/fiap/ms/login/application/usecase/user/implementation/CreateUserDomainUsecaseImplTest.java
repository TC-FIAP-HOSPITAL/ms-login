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
class CreateUserDomainUsecaseImplTest {

    @Mock
    private User user;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private CreateUserUsecaseImpl createUserUsecase;

    private UserDomain regularUserDomain;
    private UserDomain adminUserDomain;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        regularUserDomain = new UserDomain(1L, "Regular User", "regular@example.com", "regularuser",
                "password", RoleEnum.PACIENTE, now, now);

        adminUserDomain = new UserDomain(2L, "Admin User", "admin@example.com", "adminuser",
                "password", RoleEnum.ADMIN, now, now);
    }

    @Test
    void createUser_regularUser_shouldSaveAndReturnUser() {
        when(securityUtil.isAdmin()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(user.save(any(UserDomain.class))).thenReturn(regularUserDomain);

        UserDomain result = createUserUsecase.createUser(regularUserDomain);

        assertNotNull(result);
        assertEquals(regularUserDomain.getId(), result.getId());
        assertEquals(regularUserDomain.getName(), result.getName());
        assertEquals(regularUserDomain.getEmail(), result.getEmail());
        assertEquals(regularUserDomain.getUsername(), result.getUsername());
        assertEquals(regularUserDomain.getRole(), result.getRole());

        verify(passwordEncoder).encode("password");
        verify(user).save(regularUserDomain);
    }

    @Test
    void createUser_adminUserByAdmin_shouldSaveAndReturnUser() {
        when(securityUtil.isAdmin()).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(user.save(any(UserDomain.class))).thenReturn(adminUserDomain);

        UserDomain result = createUserUsecase.createUser(adminUserDomain);

        assertNotNull(result);
        assertEquals(adminUserDomain.getId(), result.getId());
        assertEquals(adminUserDomain.getName(), result.getName());
        assertEquals(adminUserDomain.getEmail(), result.getEmail());
        assertEquals(adminUserDomain.getUsername(), result.getUsername());
        assertEquals(adminUserDomain.getRole(), result.getRole());

        verify(passwordEncoder).encode("password");
        verify(user).save(adminUserDomain);
    }

    @Test
    void createUser_adminUserByNonAdmin_shouldThrowAccessDeniedException() {
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> createUserUsecase.createUser(adminUserDomain));
    }
}
