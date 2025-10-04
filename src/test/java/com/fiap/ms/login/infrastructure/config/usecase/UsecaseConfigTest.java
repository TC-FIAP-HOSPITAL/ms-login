package com.fiap.ms.login.infrastructure.config.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;

class UsecaseConfigTest {

    private CreateUserConfig createUserConfig;
    private DeleteUserConfig deleteUserConfig;
    private GetUserByIdConfig getUserByIdConfig;
    private GetUsersConfig getUsersConfig;
    private UpdateUserConfig updateUserConfig;

    private User user;
    private PasswordEncoder passwordEncoder;
    private SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        createUserConfig = new CreateUserConfig();
        deleteUserConfig = new DeleteUserConfig();
        getUserByIdConfig = new GetUserByIdConfig();
        getUsersConfig = new GetUsersConfig();
        updateUserConfig = new UpdateUserConfig();

        user = mock(User.class);
        passwordEncoder = mock(PasswordEncoder.class);
        securityUtil = mock(SecurityUtil.class);
    }

    @Test
    void createUserUsecaseBean_shouldNotBeNull() {
        assertNotNull(createUserConfig.createUserUsecase(user, passwordEncoder, securityUtil));
    }

    @Test
    void deleteUserUsecaseBean_shouldNotBeNull() {
        assertNotNull(deleteUserConfig.deleteUserUsecase(user, securityUtil));
    }

    @Test
    void getUserByIdUsecaseBean_shouldNotBeNull() {
        assertNotNull(getUserByIdConfig.getUserByIdUsecase(user, securityUtil));
    }

    @Test
    void getUsersUsecaseBean_shouldNotBeNull() {
        assertNotNull(getUsersConfig.getUsersUsecase(user, passwordEncoder, securityUtil));
    }

    @Test
    void updateUserUsecaseBean_shouldNotBeNull() {
        assertNotNull(updateUserConfig.updateUserUsecase(user, passwordEncoder, securityUtil));
    }
}
