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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersUsecaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderGateway passwordEncoderGateway;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private GetUsersUsecaseImpl getUsersUsecase;

    private User user1;
    private User user2;
    private List<User> users;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        Address address1 = new Address(1L, "Test Street", "123", "Apt 4", "Test City", "TS");
        user1 = new User(1L, "Test User 1", "test1@example.com", "testuser1", "password", Role.USER, now, now, address1);

        Address address2 = new Address(2L, "Other Street", "456", "Apt 7", "Other City", "OS");
        user2 = new User(2L, "Test User 2", "test2@example.com", "testuser2", "password", Role.USER, now, now, address2);

        users = Arrays.asList(user1, user2);
    }

    @Test
    void getUsers_adminUser_shouldReturnAllUsers() {
        Integer page = 0;
        Integer size = 10;
        when(securityUtil.isAdmin()).thenReturn(true);
        when(userRepository.findAllUsers(page, size)).thenReturn(users);

        List<User> result = getUsersUsecase.getUsers(page, size);

        assertEquals(2, result.size());
        assertEquals(user1.getId(), result.get(0).getId());
        assertEquals(user1.getName(), result.get(0).getName());
        assertEquals(user2.getId(), result.get(1).getId());
        assertEquals(user2.getName(), result.get(1).getName());
    }

    @Test
    void getUsers_nonAdminUser_shouldThrowAccessDeniedException() {
        Integer page = 0;
        Integer size = 10;
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> getUsersUsecase.getUsers(page, size));
    }
}
