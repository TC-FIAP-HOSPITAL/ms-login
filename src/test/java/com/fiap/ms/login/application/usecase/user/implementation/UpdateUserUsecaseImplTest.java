package com.fiap.ms.login.application.usecase.user.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
class UpdateUserUsecaseImplTest {

    @Mock
    private User userGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private UpdateUserUsecaseImpl updateUserUsecase;

    @Test
    void updateUser_asAdmin_canUpdateAny() {
        when(securityUtil.isAdmin()).thenReturn(true);
        when(securityUtil.getUserId()).thenReturn(1L);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userGateway.update(any())).thenReturn(
                new UserDomain(2L, "updated", "updated@email.com", "updateduser", "encoded", RoleEnum.MEDICO));

        UserDomain user = new UserDomain(2L, "updated", "updated@email.com", "updateduser", "newpass", RoleEnum.MEDICO);
        UserDomain result = updateUserUsecase.updateUser(user);

        assertEquals("updated", result.getName());
    }

    @Test
    void updateUser_asNonAdmin_canUpdateOwnWithoutRoleChange() {
        when(securityUtil.isAdmin()).thenReturn(false);
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.getRole()).thenReturn(RoleEnum.PACIENTE);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userGateway.update(any())).thenReturn(
                new UserDomain(1L, "updated", "updated@email.com", "updateduser", "encoded", RoleEnum.PACIENTE));

        UserDomain user = new UserDomain(1L, "updated", "updated@email.com", "updateduser", "newpass",
                RoleEnum.PACIENTE);
        UserDomain result = updateUserUsecase.updateUser(user);

        assertEquals("updated", result.getName());
    }

    @Test
    void updateUser_asNonAdmin_cannotUpdateOther() {
        when(securityUtil.isAdmin()).thenReturn(false);
        when(securityUtil.getUserId()).thenReturn(1L);

        UserDomain user = new UserDomain(2L, "updated", "updated@email.com", "updateduser", "newpass",
                RoleEnum.PACIENTE);
        assertThrows(AccessDeniedException.class, () -> updateUserUsecase.updateUser(user));
    }

    @Test
    void updateUser_asNonAdmin_cannotChangeRole() {
        when(securityUtil.isAdmin()).thenReturn(false);
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.getRole()).thenReturn(RoleEnum.PACIENTE);

        UserDomain user = new UserDomain(1L, "updated", "updated@email.com", "updateduser", "newpass", RoleEnum.ADMIN);
        assertThrows(AccessDeniedException.class, () -> updateUserUsecase.updateUser(user));
    }
}
