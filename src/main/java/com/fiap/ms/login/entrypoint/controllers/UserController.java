package com.fiap.ms.login.entrypoint.controllers;

import com.fiap.ms.login.application.usecase.user.CreateUserUsecase;
import com.fiap.ms.login.application.usecase.user.DeleteUserUsecase;
import com.fiap.ms.login.application.usecase.user.GetUserByIdUsecase;
import com.fiap.ms.login.application.usecase.user.GetUsersUsecase;
import com.fiap.ms.login.application.usecase.user.UpdateUserUsecase;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import com.fiap.ms.login.application.usecase.user.exceptions.UserNotFoundException;
import com.fiap.ms.login.entrypoint.controllers.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CreateUserUsecase createUserUsecase;
    private final DeleteUserUsecase deleteUserUsecase;
    private final GetUserByIdUsecase getUserByIdUsecase;
    private final GetUsersUsecase getUsersUsecase;
    private final UpdateUserUsecase updateUserUsecase;

    public UserController(
            CreateUserUsecase createUserUsecase,
            DeleteUserUsecase deleteUserUsecase,
            GetUserByIdUsecase getUserByIdUsecase,
            GetUsersUsecase getUserUsecase,
            UpdateUserUsecase updateUserUsecase
            ) {
        this.createUserUsecase = createUserUsecase;
        this.deleteUserUsecase = deleteUserUsecase;
        this.getUserByIdUsecase = getUserByIdUsecase;
        this.getUsersUsecase = getUserUsecase;
        this.updateUserUsecase = updateUserUsecase;
    }

    @Operation(summary = "Get all users. Administrators only.", description = "Returns a list of all users.")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDtoResponse>> getUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        List<User> users = getUsersUsecase.getUsers(page, size);
        List<UserDtoResponse> usersDtoResponse = users.stream()
                .map(UserMapper::domainToDto)
                .toList();
        return ResponseEntity.ok().body(usersDtoResponse);
    }

    @Operation(summary = "Get user by ID. User can only get it's own data. Admin can get any user.", description = "Returns a user by their ID.")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable("user_id") String id) {
        Optional<User> user = getUserByIdUsecase.getUserById(id);
        return user.map(value -> ResponseEntity.ok().body(UserMapper.domainToDto(value))).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Operation(summary = "Create a new user. Can only create CUSTOMER role. Admin can create any role.", description = "Creates a new user.")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @PostMapping
    public ResponseEntity<UserDtoResponse> createUser(@RequestBody UserDtoRequest userDTORequest) {
        User user = UserMapper.dtoToDomain(userDTORequest);
        User createdUser = createUserUsecase.createUser(user);
        UserDtoResponse userDtoResponse = UserMapper.domainToDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoResponse);
    }

    @Operation(summary = "Update user content. Only admin can change other users. User can change itself, except for role.", description = "Updates user data.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @PutMapping
    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UserDtoRequest userDtoRequest) {
        User user = UserMapper.dtoToDomain(userDtoRequest);
        User updatedUser = updateUserUsecase.updateUser(user);
        UserDtoResponse userDtoResponse = UserMapper.domainToDto(updatedUser);
        return ResponseEntity.ok().body(userDtoResponse);
    }

    @Operation(summary = "Excludes an user. Only admin can exclude other users. User can exclude itself.", description = "Excludes an user.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") String id ) {
        deleteUserUsecase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
