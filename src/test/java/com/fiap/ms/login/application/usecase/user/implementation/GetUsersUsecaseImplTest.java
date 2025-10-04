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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersUsecaseImplTest {

    @Mock
    private User user;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private GetUsersUsecaseImpl getUsersUsecase;

    private UserDomain userDomain1;
    private UserDomain userDomain2;
    private List<UserDomain> userDomains;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        userDomain1 = new UserDomain(1L, "Test User 1", "test1@example.com", "testuser1", "password", RoleEnum.PACIENTE, now, now);
        userDomain2 = new UserDomain(2L, "Test User 2", "test2@example.com", "testuser2", "password", RoleEnum.PACIENTE, now, now);
        userDomains = Arrays.asList(userDomain1, userDomain2);
    }

    @Test
    void getUsers_adminUser_shouldReturnAllUsers() {
        Integer page = 0;
        Integer size = 10;
        when(securityUtil.isAdmin()).thenReturn(true);
        when(user.findAllUsers(page, size)).thenReturn(userDomains);

        List<UserDomain> result = getUsersUsecase.getUsers(page, size);

        assertEquals(2, result.size());
        assertEquals(userDomain1.getId(), result.get(0).getId());
        assertEquals(userDomain1.getName(), result.get(0).getName());
        assertEquals(userDomain2.getId(), result.get(1).getId());
        assertEquals(userDomain2.getName(), result.get(1).getName());
    }

    @Test
    void getUsers_nonAdminUser_shouldThrowAccessDeniedException() {
        Integer page = 0;
        Integer size = 10;
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> getUsersUsecase.getUsers(page, size));
    }
}
