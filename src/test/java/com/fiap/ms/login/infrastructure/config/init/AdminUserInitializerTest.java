package com.fiap.ms.login.infrastructure.config.init;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;

class AdminUserInitializerTest {

    private User userGateway;
    private PasswordEncoder passwordEncoder;
    private AdminUserInitializer initializer;

    @BeforeEach
    void setUp() {
        userGateway = mock(User.class);
        passwordEncoder = mock(PasswordEncoder.class);
        initializer = new AdminUserInitializer(userGateway, passwordEncoder);
        ReflectionTestUtils.setField(initializer, "adminPassword", "adminPassword123");
    }

    @Test
    void init_shouldCreateAdminWhenMissing() {
        when(userGateway.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("adminPassword123")).thenReturn("encodedPassword");

        initializer.init();

        ArgumentCaptor<UserDomain> captor = ArgumentCaptor.forClass(UserDomain.class);
        verify(userGateway).save(captor.capture());
        UserDomain saved = captor.getValue();
        assertEquals("Administrator", saved.getName());
        assertEquals("admin", saved.getUsername());
        assertEquals("encodedPassword", saved.getPassword());
        assertEquals(RoleEnum.ADMIN, saved.getRole());
    }

    @Test
    void init_shouldNotCreateWhenAdminExists() {
        when(userGateway.findByUsername("admin")).thenReturn(Optional.of(new UserDomain()));

        initializer.init();

        verify(userGateway).findByUsername("admin");
        verifyNoMoreInteractions(userGateway);
    }
}
