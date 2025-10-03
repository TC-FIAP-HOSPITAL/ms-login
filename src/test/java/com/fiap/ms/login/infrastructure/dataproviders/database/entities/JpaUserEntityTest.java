package com.fiap.ms.login.infrastructure.dataproviders.database.entities;

import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JpaUserEntityTest {

    @Test
    void testNoArgsConstructor() {
        JpaUserEntity entity = new JpaUserEntity();
        
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getEmail());
        assertNull(entity.getUsername());
        assertNull(entity.getPassword());
        assertNull(entity.getRole());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
        assertFalse(entity.isDeleted());
        assertNull(entity.getDeletedAt());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String name = "Test User";
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        Role role = Role.PACIENTE;
        LocalDateTime now = LocalDateTime.now();
        boolean isDeleted = false;
        LocalDateTime deletedAt = null;

        JpaUserEntity entity = new JpaUserEntity(id, name, email, username, password, role, now, now, isDeleted, deletedAt);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(email, entity.getEmail());
        assertEquals(username, entity.getUsername());
        assertEquals(password, entity.getPassword());
        assertEquals(role, entity.getRole());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertEquals(isDeleted, entity.isDeleted());
        assertEquals(deletedAt, entity.getDeletedAt());
    }

    @Test
    void testUserConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "Test User", "test@example.com", "testuser", "password123", Role.PACIENTE, now, now);

        JpaUserEntity entity = new JpaUserEntity(user);

        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getName(), entity.getName());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getUsername(), entity.getUsername());
        assertEquals(user.getPassword(), entity.getPassword());
        assertEquals(user.getRole(), entity.getRole());
        assertFalse(entity.isDeleted());
    }

    @Test
    void testGettersAndSetters() {
        JpaUserEntity entity = new JpaUserEntity();
        LocalDateTime now = LocalDateTime.now();

        entity.setId(1L);
        entity.setName("Test User");
        entity.setEmail("test@example.com");
        entity.setUsername("testuser");
        entity.setPassword("password123");
        entity.setRole(Role.ADMIN);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setDeleted(true);
        entity.setDeletedAt(now);

        assertEquals(1L, entity.getId());
        assertEquals("Test User", entity.getName());
        assertEquals("test@example.com", entity.getEmail());
        assertEquals("testuser", entity.getUsername());
        assertEquals("password123", entity.getPassword());
        assertEquals(Role.ADMIN, entity.getRole());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertTrue(entity.isDeleted());
        assertEquals(now, entity.getDeletedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        JpaUserEntity entity1 = new JpaUserEntity(1L, "Test User", "test@example.com", "testuser", "password123", Role.PACIENTE, now, now, false, null);
        JpaUserEntity entity2 = new JpaUserEntity(1L, "Test User", "test@example.com", "testuser", "password123", Role.PACIENTE, now, now, false, null);
        JpaUserEntity entity3 = new JpaUserEntity(2L, "Other User", "other@example.com", "otheruser", "password456", Role.ADMIN, now, now, false, null);

        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        JpaUserEntity entity = new JpaUserEntity(1L, "Test User", "test@example.com", "testuser", "password123", Role.PACIENTE, now, now, false, null);

        String toString = entity.toString();

        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("PACIENTE"));
    }

    @Test
    void testRoleValues() {
        JpaUserEntity entity = new JpaUserEntity();

        entity.setRole(Role.ADMIN);
        assertEquals(Role.ADMIN, entity.getRole());

        entity.setRole(Role.MEDICO);
        assertEquals(Role.MEDICO, entity.getRole());

        entity.setRole(Role.ENFERMEIRO);
        assertEquals(Role.ENFERMEIRO, entity.getRole());

        entity.setRole(Role.PACIENTE);
        assertEquals(Role.PACIENTE, entity.getRole());
    }

    @Test
    void testBooleanFields() {
        JpaUserEntity entity = new JpaUserEntity();

        assertFalse(entity.isDeleted());

        entity.setDeleted(true);
        assertTrue(entity.isDeleted());

        entity.setDeleted(false);
        assertFalse(entity.isDeleted());
    }

    @Test
    void testDateFields() {
        JpaUserEntity entity = new JpaUserEntity();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusDays(1);

        entity.setCreatedAt(now);
        entity.setUpdatedAt(later);
        entity.setDeletedAt(later);

        assertEquals(now, entity.getCreatedAt());
        assertEquals(later, entity.getUpdatedAt());
        assertEquals(later, entity.getDeletedAt());
    }
}