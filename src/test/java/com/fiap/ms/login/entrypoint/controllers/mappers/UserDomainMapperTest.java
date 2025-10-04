package com.fiap.ms.login.entrypoint.controllers.mappers;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import com.fiap.ms.login.infrastructure.database.entities.JpaUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDomainMapperTest {

    private UserDomain userDomain;
    private JpaUserEntity userEntity;
    private UserDtoRequest userDtoRequest;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        userDomain = new UserDomain(1L, "Test User", "test@example.com", "testuser", "password", RoleEnum.PACIENTE, now, now);

        userDtoRequest = new UserDtoRequest("1", "Test User", "test@example.com", "testuser", "password", "PACIENTE");

        userEntity = new JpaUserEntity();
        userEntity.setId(1L);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");
        userEntity.setUsername("testuser");
        userEntity.setPassword("password");
        userEntity.setRoleEnum(RoleEnum.PACIENTE);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);
    }

    @Test
    void entityToDomain_shouldMapCorrectly() {
        UserDomain result = UserMapper.entityToDomain(userEntity);

        assertEquals(userEntity.getId(), result.getId());
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getEmail(), result.getEmail());
        assertEquals(userEntity.getUsername(), result.getUsername());
        assertEquals(userEntity.getPassword(), result.getPassword());
        assertEquals(userEntity.getRoleEnum(), result.getRole());
        assertEquals(userEntity.getCreatedAt(), result.getCreatedAt());
        assertEquals(userEntity.getUpdatedAt(), result.getUpdatedAt());
    }

    @Test
    void domainToEntity_shouldMapCorrectly() {
        JpaUserEntity result = UserMapper.domainToEntity(userDomain);

        assertEquals(userDomain.getId(), result.getId());
        assertEquals(userDomain.getName(), result.getName());
        assertEquals(userDomain.getEmail(), result.getEmail());
        assertEquals(userDomain.getUsername(), result.getUsername());
        assertEquals(userDomain.getPassword(), result.getPassword());
        assertEquals(userDomain.getRole(), result.getRoleEnum());
        assertFalse(result.isDeleted());
    }

    @Test
    void domainToDto_shouldMapCorrectly() {
        UserDtoResponse result = UserMapper.domainToDto(userDomain);

        assertEquals(userDomain.getId().toString(), result.id());
        assertEquals(userDomain.getName(), result.name());
        assertEquals(userDomain.getEmail(), result.email());
        assertEquals(userDomain.getUsername(), result.username());
        assertEquals(userDomain.getRole().toString(), result.role());
        assertEquals(userDomain.getCreatedAt(), result.createdAt());
        assertEquals(userDomain.getUpdatedAt(), result.modifiedAt());
    }

    @Test
    void dtoToDomain_shouldMapCorrectly() {
        UserDomain result = UserMapper.dtoToDomain(userDtoRequest);

        assertEquals(Long.valueOf(userDtoRequest.id()), result.getId());
        assertEquals(userDtoRequest.name(), result.getName());
        assertEquals(userDtoRequest.email(), result.getEmail());
        assertEquals(userDtoRequest.username(), result.getUsername());
        assertEquals(userDtoRequest.password(), result.getPassword());
        assertEquals(RoleEnum.valueOf(userDtoRequest.role()), result.getRole());
    }

    @Test
    void dtoToDomain_withNullId_shouldMapCorrectly() {
        UserDtoRequest dtoWithNullId = new UserDtoRequest(null, "Test User", "test@example.com", "testuser", "password", "PACIENTE");

        UserDomain result = UserMapper.dtoToDomain(dtoWithNullId);

        assertNull(result.getId());
        assertEquals(dtoWithNullId.name(), result.getName());
        assertEquals(dtoWithNullId.email(), result.getEmail());
        assertEquals(dtoWithNullId.username(), result.getUsername());
        assertEquals(dtoWithNullId.password(), result.getPassword());
        assertEquals(RoleEnum.valueOf(dtoWithNullId.role()), result.getRole());
    }
}
