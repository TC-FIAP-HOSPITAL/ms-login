package com.fiap.ms.login.infrastructure.database.entities;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JpaUserDomainEntityTest {

    @Test
    void testNoArgsConstructor() {
        JpaUserEntity entity = new JpaUserEntity();
        
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getEmail());
        assertNull(entity.getUsername());
        assertNull(entity.getPassword());
        assertNull(entity.getRoleEnum());
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
        RoleEnum roleEnum = RoleEnum.PACIENTE;
        LocalDateTime now = LocalDateTime.now();
        boolean isDeleted = false;
        LocalDateTime deletedAt = null;

        JpaUserEntity entity = new JpaUserEntity(id, name, email, username, password, roleEnum, now, now, isDeleted, deletedAt);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(email, entity.getEmail());
        assertEquals(username, entity.getUsername());
        assertEquals(password, entity.getPassword());
        assertEquals(roleEnum, entity.getRoleEnum());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertEquals(isDeleted, entity.isDeleted());
        assertEquals(deletedAt, entity.getDeletedAt());
    }

    @Test
    void testUserConstructor() {
        LocalDateTime now = LocalDateTime.now();
        UserDomain userDomain = new UserDomain(1L, "Test User", "test@example.com", "testuser", "password123", RoleEnum.PACIENTE, now, now);

        JpaUserEntity entity = new JpaUserEntity(userDomain);

        assertEquals(userDomain.getId(), entity.getId());
        assertEquals(userDomain.getName(), entity.getName());
        assertEquals(userDomain.getEmail(), entity.getEmail());
        assertEquals(userDomain.getUsername(), entity.getUsername());
        assertEquals(userDomain.getPassword(), entity.getPassword());
        assertEquals(userDomain.getRole(), entity.getRoleEnum());
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
        entity.setRoleEnum(RoleEnum.ADMIN);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setDeleted(true);
        entity.setDeletedAt(now);

        assertEquals(1L, entity.getId());
        assertEquals("Test User", entity.getName());
        assertEquals("test@example.com", entity.getEmail());
        assertEquals("testuser", entity.getUsername());
        assertEquals("password123", entity.getPassword());
        assertEquals(RoleEnum.ADMIN, entity.getRoleEnum());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertTrue(entity.isDeleted());
        assertEquals(now, entity.getDeletedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        JpaUserEntity entity1 = new JpaUserEntity(1L, "Test User", "test@example.com", "testuser", "password123", RoleEnum.PACIENTE, now, now, false, null);
        JpaUserEntity entity2 = new JpaUserEntity(1L, "Test User", "test@example.com", "testuser", "password123", RoleEnum.PACIENTE, now, now, false, null);
        JpaUserEntity entity3 = new JpaUserEntity(2L, "Other User", "other@example.com", "otheruser", "password456", RoleEnum.ADMIN, now, now, false, null);

        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        JpaUserEntity entity = new JpaUserEntity(1L, "Test User", "test@example.com", "testuser", "password123", RoleEnum.PACIENTE, now, now, false, null);

        String toString = entity.toString();

        assertTrue(toString.contains("Test User"));
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("PACIENTE"));
    }

    @Test
    void testRoleValues() {
        JpaUserEntity entity = new JpaUserEntity();

        entity.setRoleEnum(RoleEnum.ADMIN);
        assertEquals(RoleEnum.ADMIN, entity.getRoleEnum());

        entity.setRoleEnum(RoleEnum.MEDICO);
        assertEquals(RoleEnum.MEDICO, entity.getRoleEnum());

        entity.setRoleEnum(RoleEnum.ENFERMEIRO);
        assertEquals(RoleEnum.ENFERMEIRO, entity.getRoleEnum());

        entity.setRoleEnum(RoleEnum.PACIENTE);
        assertEquals(RoleEnum.PACIENTE, entity.getRoleEnum());
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