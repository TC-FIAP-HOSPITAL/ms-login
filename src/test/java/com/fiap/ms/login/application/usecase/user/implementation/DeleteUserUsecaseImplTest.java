package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.usecase.user.exceptions.UserHasException;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUsecaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private DeleteUserUsecaseImpl deleteUserUsecase;

    @Test
    void deleteUser_sameUser_shouldDeleteUser() {
        String userId = "1";
        when(securityUtil.getUserId()).thenReturn(1L);
        doNothing().when(userRepository).delete(1L);

        deleteUserUsecase.deleteUser(userId);

        verify(userRepository).delete(1L);
    }

    @Test
    void deleteUser_adminDeletingOtherUser_shouldDeleteUser() {
        String userId = "2";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(true);
        doNothing().when(userRepository).delete(2L);

        deleteUserUsecase.deleteUser(userId);

        verify(userRepository).delete(2L);
    }

    @Test
    void deleteUser_nonAdminDeletingOtherUser_shouldThrowAccessDeniedException() {
        String userId = "2";
        when(securityUtil.getUserId()).thenReturn(1L);
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> deleteUserUsecase.deleteUser(userId));
        verify(userRepository, never()).delete(anyLong());
    }
}
