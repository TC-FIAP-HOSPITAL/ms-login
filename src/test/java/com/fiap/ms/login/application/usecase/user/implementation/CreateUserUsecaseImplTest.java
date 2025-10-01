package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoderGateway;
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
class CreateUserUsecaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderGateway passwordEncoderGateway;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private CreateUserUsecaseImpl createUserUsecase;

    private User regularUser;
    private User adminUser;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        regularUser = new User(1L, "Regular User", "regular@example.com", "regularuser", 
                "password", Role.PACIENTE, now, now);

        adminUser = new User(2L, "Admin User", "admin@example.com", "adminuser", 
                "password", Role.ADMIN, now, now);
    }

    @Test
    void createUser_regularUser_shouldSaveAndReturnUser() {
        when(securityUtil.isAdmin()).thenReturn(false);
        when(passwordEncoderGateway.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(regularUser);

        User result = createUserUsecase.createUser(regularUser);

        assertNotNull(result);
        assertEquals(regularUser.getId(), result.getId());
        assertEquals(regularUser.getName(), result.getName());
        assertEquals(regularUser.getEmail(), result.getEmail());
        assertEquals(regularUser.getUsername(), result.getUsername());
        assertEquals(regularUser.getRole(), result.getRole());

        verify(passwordEncoderGateway).encode("password");
        verify(userRepository).save(regularUser);
    }

    @Test
    void createUser_adminUserByAdmin_shouldSaveAndReturnUser() {
        when(securityUtil.isAdmin()).thenReturn(true);
        when(passwordEncoderGateway.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(adminUser);

        User result = createUserUsecase.createUser(adminUser);

        assertNotNull(result);
        assertEquals(adminUser.getId(), result.getId());
        assertEquals(adminUser.getName(), result.getName());
        assertEquals(adminUser.getEmail(), result.getEmail());
        assertEquals(adminUser.getUsername(), result.getUsername());
        assertEquals(adminUser.getRole(), result.getRole());

        verify(passwordEncoderGateway).encode("password");
        verify(userRepository).save(adminUser);
    }

    @Test
    void createUser_adminUserByNonAdmin_shouldThrowAccessDeniedException() {
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> createUserUsecase.createUser(adminUser));
    }
}
