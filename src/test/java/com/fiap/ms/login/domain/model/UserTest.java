package com.fiap.ms.login.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void defaultConstructor_shouldCreateEmptyUser() {
        User user = new User();
        
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

    @Test
    void constructorWithBasicFields_shouldSetFields() {
        Long id = 1L;
        String name = "Test User";
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        Role role = Role.PACIENTE;

        User user = new User(id, name, email, username, password, role);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void constructorWithAllFields_shouldSetAllFields() {
        Long id = 1L;
        String name = "Test User";
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        Role role = Role.ADMIN;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User user = new User(id, name, email, username, password, role, createdAt, updatedAt);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        User user = new User();
        Long id = 2L;
        String name = "Updated User";
        String email = "updated@example.com";
        String username = "updateduser";
        String password = "newpassword";
        Role role = Role.ADMIN;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());
    }
}