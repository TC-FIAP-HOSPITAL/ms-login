package com.fiap.ms.login.entrypoint.controllers;

import com.fiap.ms.login.application.usecase.user.CreateUserUsecase;
import com.fiap.ms.login.application.usecase.user.DeleteUserUsecase;
import com.fiap.ms.login.application.usecase.user.GetUserByIdUsecase;
import com.fiap.ms.login.application.usecase.user.GetUsersUsecase;
import com.fiap.ms.login.application.usecase.user.UpdateUserUsecase;
import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import com.fiap.ms.login.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDomainControllerTest {

    @Mock
    private CreateUserUsecase createUserUsecase;

    @Mock
    private DeleteUserUsecase deleteUserUsecase;

    @Mock
    private GetUserByIdUsecase getUserByIdUsecase;

    @Mock
    private GetUsersUsecase getUsersUsecase;

    @Mock
    private UpdateUserUsecase updateUserUsecase;

    @InjectMocks
    private UserController userController;

    private UserDomain userDomain1;
    private UserDomain userDomain2;
    private UserDtoRequest userDtoRequest;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        userDomain1 = new UserDomain(1L, "Test User 1", "test1@example.com", "testuser1", "password", RoleEnum.PACIENTE, now, now);
        userDomain2 = new UserDomain(2L, "Test User 2", "test2@example.com", "testuser2", "password", RoleEnum.PACIENTE, now, now);
        userDtoRequest = new UserDtoRequest("1", "Test User 1", "test1@example.com", "testuser1", "password", "PACIENTE");
    }

    @Test
    void getUsers_shouldReturnAllUsers() {
        List<UserDomain> userDomains = Arrays.asList(userDomain1, userDomain2);
        when(getUsersUsecase.getUsers(anyInt(), anyInt())).thenReturn(userDomains);

        ResponseEntity<List<UserDtoResponse>> response = userController.getUsers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("1", response.getBody().get(0).id());
        assertEquals("Test User 1", response.getBody().get(0).name());
        assertEquals("2", response.getBody().get(1).id());
        assertEquals("Test User 2", response.getBody().get(1).name());
    }

    @Test
    void getUser_existingUser_shouldReturnUser() {
        when(getUserByIdUsecase.getUserById("1")).thenReturn(Optional.of(userDomain1));

        ResponseEntity<UserDtoResponse> response = userController.getUser("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().id());
        assertEquals("Test User 1", response.getBody().name());
        assertEquals("test1@example.com", response.getBody().email());
        assertEquals("testuser1", response.getBody().username());
        assertEquals("PACIENTE", response.getBody().role());
    }

    @Test
    void getUser_nonExistingUser_shouldThrowUserNotFoundException() {
        when(getUserByIdUsecase.getUserById("999")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.getUser("999"));
    }

    @Test
    void createUser_shouldCreateAndReturnUser() {
        when(createUserUsecase.createUser(any(UserDomain.class))).thenReturn(userDomain1);

        ResponseEntity<UserDtoResponse> response = userController.createUser(userDtoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("1", response.getBody().id());
        assertEquals("Test User 1", response.getBody().name());
        assertEquals("test1@example.com", response.getBody().email());
        assertEquals("testuser1", response.getBody().username());
        assertEquals("PACIENTE", response.getBody().role());
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        when(updateUserUsecase.updateUser(any(UserDomain.class))).thenReturn(userDomain1);

        ResponseEntity<UserDtoResponse> response = userController.updateUser(userDtoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().id());
        assertEquals("Test User 1", response.getBody().name());
        assertEquals("test1@example.com", response.getBody().email());
        assertEquals("testuser1", response.getBody().username());
        assertEquals("PACIENTE", response.getBody().role());
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        doNothing().when(deleteUserUsecase).deleteUser("1");

        ResponseEntity<Void> response = userController.deleteUser("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteUserUsecase).deleteUser("1");
    }
}
