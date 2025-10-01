package com.fiap.ms.login.entrypoint.controllers.mappers;

import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private User user;
    private JpaUserEntity userEntity;
    private UserDtoRequest userDtoRequest;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        user = new User(1L, "Test User", "test@example.com", "testuser", "password", Role.PACIENTE, now, now);

        userDtoRequest = new UserDtoRequest("1", "Test User", "test@example.com", "testuser", "password", "PACIENTE");

        userEntity = new JpaUserEntity();
        userEntity.setId(1L);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");
        userEntity.setUsername("testuser");
        userEntity.setPassword("password");
        userEntity.setRole(Role.PACIENTE);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);
    }

    @Test
    void entityToDomain_shouldMapCorrectly() {
        User result = UserMapper.entityToDomain(userEntity);

        assertEquals(userEntity.getId(), result.getId());
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getEmail(), result.getEmail());
        assertEquals(userEntity.getUsername(), result.getUsername());
        assertEquals(userEntity.getPassword(), result.getPassword());
        assertEquals(userEntity.getRole(), result.getRole());
        assertEquals(userEntity.getCreatedAt(), result.getCreatedAt());
        assertEquals(userEntity.getUpdatedAt(), result.getUpdatedAt());
    }

    @Test
    void domainToEntity_shouldMapCorrectly() {
        JpaUserEntity result = UserMapper.domainToEntity(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getRole(), result.getRole());
        assertFalse(result.isDeleted());
    }

    @Test
    void domainToDto_shouldMapCorrectly() {
        UserDtoResponse result = UserMapper.domainToDto(user);

        assertEquals(user.getId().toString(), result.id());
        assertEquals(user.getName(), result.name());
        assertEquals(user.getEmail(), result.email());
        assertEquals(user.getUsername(), result.username());
        assertEquals(user.getRole().toString(), result.role());
        assertEquals(user.getCreatedAt(), result.createdAt());
        assertEquals(user.getUpdatedAt(), result.modifiedAt());
    }

    @Test
    void dtoToDomain_shouldMapCorrectly() {
        User result = UserMapper.dtoToDomain(userDtoRequest);

        assertEquals(Long.valueOf(userDtoRequest.id()), result.getId());
        assertEquals(userDtoRequest.name(), result.getName());
        assertEquals(userDtoRequest.email(), result.getEmail());
        assertEquals(userDtoRequest.username(), result.getUsername());
        assertEquals(userDtoRequest.password(), result.getPassword());
        assertEquals(Role.valueOf(userDtoRequest.role()), result.getRole());
    }

    @Test
    void dtoToDomain_withNullId_shouldMapCorrectly() {
        UserDtoRequest dtoWithNullId = new UserDtoRequest(null, "Test User", "test@example.com", "testuser", "password", "PACIENTE");

        User result = UserMapper.dtoToDomain(dtoWithNullId);

        assertNull(result.getId());
        assertEquals(dtoWithNullId.name(), result.getName());
        assertEquals(dtoWithNullId.email(), result.getEmail());
        assertEquals(dtoWithNullId.username(), result.getUsername());
        assertEquals(dtoWithNullId.password(), result.getPassword());
        assertEquals(Role.valueOf(dtoWithNullId.role()), result.getRole());
    }
}
