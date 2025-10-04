package com.fiap.ms.login.domain.model;

import com.fiap.ms.login.domain.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDomainTest {

    @Test
    void defaultConstructor_shouldCreateEmptyUser() {
        UserDomain userDomain = new UserDomain();
        
        assertNull(userDomain.getId());
        assertNull(userDomain.getName());
        assertNull(userDomain.getEmail());
        assertNull(userDomain.getUsername());
        assertNull(userDomain.getPassword());
        assertNull(userDomain.getRole());
        assertNull(userDomain.getCreatedAt());
        assertNull(userDomain.getUpdatedAt());
    }

    @Test
    void constructorWithBasicFields_shouldSetFields() {
        Long id = 1L;
        String name = "Test User";
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        RoleEnum roleEnum = RoleEnum.PACIENTE;

        UserDomain userDomain = new UserDomain(id, name, email, username, password, roleEnum);

        assertEquals(id, userDomain.getId());
        assertEquals(name, userDomain.getName());
        assertEquals(email, userDomain.getEmail());
        assertEquals(username, userDomain.getUsername());
        assertEquals(password, userDomain.getPassword());
        assertEquals(roleEnum, userDomain.getRole());
    }

    @Test
    void constructorWithAllFields_shouldSetAllFields() {
        Long id = 1L;
        String name = "Test User";
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        RoleEnum roleEnum = RoleEnum.ADMIN;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        UserDomain userDomain = new UserDomain(id, name, email, username, password, roleEnum, createdAt, updatedAt);

        assertEquals(id, userDomain.getId());
        assertEquals(name, userDomain.getName());
        assertEquals(email, userDomain.getEmail());
        assertEquals(username, userDomain.getUsername());
        assertEquals(password, userDomain.getPassword());
        assertEquals(roleEnum, userDomain.getRole());
        assertEquals(createdAt, userDomain.getCreatedAt());
        assertEquals(updatedAt, userDomain.getUpdatedAt());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        UserDomain userDomain = new UserDomain();
        Long id = 2L;
        String name = "Updated User";
        String email = "updated@example.com";
        String username = "updateduser";
        String password = "newpassword";
        RoleEnum roleEnum = RoleEnum.ADMIN;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        userDomain.setId(id);
        userDomain.setName(name);
        userDomain.setEmail(email);
        userDomain.setUsername(username);
        userDomain.setPassword(password);
        userDomain.setRole(roleEnum);
        userDomain.setCreatedAt(createdAt);
        userDomain.setUpdatedAt(updatedAt);

        assertEquals(id, userDomain.getId());
        assertEquals(name, userDomain.getName());
        assertEquals(email, userDomain.getEmail());
        assertEquals(username, userDomain.getUsername());
        assertEquals(password, userDomain.getPassword());
        assertEquals(roleEnum, userDomain.getRole());
        assertEquals(createdAt, userDomain.getCreatedAt());
        assertEquals(updatedAt, userDomain.getUpdatedAt());
    }
}