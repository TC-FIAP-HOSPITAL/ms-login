package com.fiap.ms.login.application.usecase.user.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;

@ExtendWith(MockitoExtension.class)
class CreateUserUsecaseImplTest {

    @Mock
    private User userGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private CreateUserUsecaseImpl createUserUsecase;

    @Test
    void createUser_asAdmin_canCreateAnyRole() {
        when(securityUtil.isAdmin()).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userGateway.save(any()))
                .thenReturn(new UserDomain(1L, "name", "email", "user", "encoded", RoleEnum.ADMIN));

        UserDomain user = new UserDomain(null, "name", "email", "user", "pass", RoleEnum.ADMIN);
        UserDomain result = createUserUsecase.createUser(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void createUser_asNonAdmin_canOnlyCreatePaciente() {
        when(securityUtil.isAdmin()).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userGateway.save(any()))
                .thenReturn(new UserDomain(1L, "name", "email", "user", "encoded", RoleEnum.PACIENTE));

        UserDomain user = new UserDomain(null, "name", "email", "user", "pass", RoleEnum.PACIENTE);
        UserDomain result = createUserUsecase.createUser(user);

        assertNotNull(result);
    }

    @Test
    void createUser_asNonAdmin_cannotCreateAdmin() {
        when(securityUtil.isAdmin()).thenReturn(false);

        UserDomain user = new UserDomain(null, "name", "email", "user", "pass", RoleEnum.ADMIN);
        assertThrows(AccessDeniedException.class, () -> createUserUsecase.createUser(user));
    }
}
