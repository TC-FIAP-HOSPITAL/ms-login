package com.fiap.ms.login.entrypoint.controllers;

import com.fiap.ms.login.application.usecase.user.CreateUserUsecase;
import com.fiap.ms.login.application.usecase.user.DeleteUserUsecase;
import com.fiap.ms.login.application.usecase.user.GetUserByIdUsecase;
import com.fiap.ms.login.application.usecase.user.GetUsersUsecase;
import com.fiap.ms.login.application.usecase.user.UpdateUserUsecase;
import com.fiap.ms.login.domain.model.Address;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.entrypoint.controllers.dto.AddressDto;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import com.fiap.ms.login.application.usecase.user.exceptions.UserNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

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

    private User user1;
    private User user2;
    private UserDtoRequest userDtoRequest;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        Address address1 = new Address(1L, "Test Street", "123", "Apt 4", "Test City", "TS");
        user1 = new User(1L, "Test User 1", "test1@example.com", "testuser1", "password", Role.USER, now, now, address1);

        Address address2 = new Address(2L, "Other Street", "456", "Apt 7", "Other City", "OS");
        user2 = new User(2L, "Test User 2", "test2@example.com", "testuser2", "password", Role.USER, now, now, address2);

        addressDto = new AddressDto("1", "Test Street", "123", "Apt 4", "Test City", "TS");
        userDtoRequest = new UserDtoRequest("1", "Test User 1", "test1@example.com", "testuser1", "password", "USER", addressDto);
    }

    @Test
    void getUsers_shouldReturnAllUsers() {
        List<User> users = Arrays.asList(user1, user2);
        when(getUsersUsecase.getUsers(anyInt(), anyInt())).thenReturn(users);

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
        when(getUserByIdUsecase.getUserById("1")).thenReturn(Optional.of(user1));

        ResponseEntity<UserDtoResponse> response = userController.getUser("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().id());
        assertEquals("Test User 1", response.getBody().name());
        assertEquals("test1@example.com", response.getBody().email());
        assertEquals("testuser1", response.getBody().username());
        assertEquals("USER", response.getBody().role());
    }

    @Test
    void getUser_nonExistingUser_shouldThrowUserNotFoundException() {
        when(getUserByIdUsecase.getUserById("999")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.getUser("999"));
    }

    @Test
    void createUser_shouldCreateAndReturnUser() {
        when(createUserUsecase.createUser(any(User.class))).thenReturn(user1);

        ResponseEntity<UserDtoResponse> response = userController.createUser(userDtoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("1", response.getBody().id());
        assertEquals("Test User 1", response.getBody().name());
        assertEquals("test1@example.com", response.getBody().email());
        assertEquals("testuser1", response.getBody().username());
        assertEquals("USER", response.getBody().role());
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        when(updateUserUsecase.updateUser(any(User.class))).thenReturn(user1);

        ResponseEntity<UserDtoResponse> response = userController.updateUser(userDtoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().id());
        assertEquals("Test User 1", response.getBody().name());
        assertEquals("test1@example.com", response.getBody().email());
        assertEquals("testuser1", response.getBody().username());
        assertEquals("USER", response.getBody().role());
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        doNothing().when(deleteUserUsecase).deleteUser("1");

        ResponseEntity<Void> response = userController.deleteUser("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteUserUsecase).deleteUser("1");
    }
}
