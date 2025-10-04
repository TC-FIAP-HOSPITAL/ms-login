package com.fiap.ms.login.infrastructure.database.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fiap.ms.login.domain.enums.RoleEnum;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fiap.ms.login.infrastructure.database.repositories.JpaUserRepositoryGateway;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.database.entities.JpaUserEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserDomainRepositoryImplTest {

    @Mock
    private JpaUserRepositoryGateway jpaUserRepositoryGateway;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Filter filter;

    @InjectMocks
    private UserImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserImpl(jpaUserRepositoryGateway, entityManager);
        // Setup common mocks for Session and Filter operations
        when(entityManager.unwrap(any(Class.class))).thenReturn(session);
        when(session.enableFilter("deletedFilter")).thenReturn(filter);
        when(filter.setParameter("isDeleted", false)).thenReturn(filter);
    }

    @Test
    void save_shouldSaveUser() {
        UserDomain userDomain = new UserDomain();
        userDomain.setName("Test User");
        JpaUserEntity entity = new JpaUserEntity(userDomain);
        when(jpaUserRepositoryGateway.save(any(JpaUserEntity.class))).thenReturn(entity);

        UserDomain result = userRepository.save(userDomain);

        assertNotNull(result);
        verify(jpaUserRepositoryGateway).save(any(JpaUserEntity.class));
    }

    @Test
    void delete_shouldCallRepository() {
        Long userId = 1L;
        JpaUserEntity entity = new JpaUserEntity();
        entity.setId(userId);

        when(jpaUserRepositoryGateway.findById(userId)).thenReturn(Optional.of(entity));
        when(jpaUserRepositoryGateway.save(entity)).thenReturn(entity);

        userRepository.delete(userId);

        assertTrue(entity.isDeleted());
        verify(jpaUserRepositoryGateway).save(entity);
    }

    @Test
    void update_shouldUpdateUser() {
        UserDomain userDomain = new UserDomain();
        userDomain.setId(1L);
        userDomain.setName("Updated User");
        userDomain.setEmail("updated@test.com");

        JpaUserEntity existingEntity = new JpaUserEntity();
        existingEntity.setId(1L);
        existingEntity.setName("Old Name");

        when(jpaUserRepositoryGateway.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(jpaUserRepositoryGateway.save(any(JpaUserEntity.class))).thenReturn(existingEntity);

        UserDomain result = userRepository.update(userDomain);

        assertNotNull(result);
        verify(jpaUserRepositoryGateway).findById(1L);
        verify(jpaUserRepositoryGateway).save(any(JpaUserEntity.class));
    }

    @Test
    void update_shouldThrowExceptionWhenUserNotFound() {
        UserDomain userDomain = new UserDomain();
        userDomain.setId(1L);

        when(jpaUserRepositoryGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userRepository.update(userDomain));
    }

    @Test
    void findAllUsers_shouldReturnUsers() {
        JpaUserEntity entity1 = new JpaUserEntity();
        entity1.setId(1L);
        entity1.setName("User 1");
        entity1.setEmail("user1@test.com");
        entity1.setUsername("user1");
        entity1.setPassword("password1");
        entity1.setRoleEnum(RoleEnum.PACIENTE);
        entity1.setCreatedAt(LocalDateTime.now());
        entity1.setUpdatedAt(LocalDateTime.now());

        JpaUserEntity entity2 = new JpaUserEntity();
        entity2.setId(2L);
        entity2.setName("User 2");
        entity2.setEmail("user2@test.com");
        entity2.setUsername("user2");
        entity2.setPassword("password2");
        entity2.setRoleEnum(RoleEnum.PACIENTE);
        entity2.setCreatedAt(LocalDateTime.now());
        entity2.setUpdatedAt(LocalDateTime.now());

        Page<JpaUserEntity> page = new PageImpl<>(Arrays.asList(entity1, entity2));

        when(jpaUserRepositoryGateway.findAll(any(PageRequest.class))).thenReturn(page);

        List<UserDomain> result = userRepository.findAllUsers(0, 10);

        assertEquals(2, result.size());
        verify(entityManager).unwrap(Session.class);
        verify(session).enableFilter("deletedFilter");
        verify(filter).setParameter("isDeleted", false);
    }

    @Test
    void findById_shouldReturnUser() {
        Long userId = 1L;
        JpaUserEntity entity = new JpaUserEntity();
        entity.setId(userId);
        entity.setName("Test User");
        entity.setEmail("test@test.com");
        entity.setUsername("testuser");
        entity.setPassword("password123");
        entity.setRoleEnum(RoleEnum.PACIENTE);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        when(jpaUserRepositoryGateway.findById(userId)).thenReturn(Optional.of(entity));

        Optional<UserDomain> result = userRepository.findById(userId);

        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getName());
        verify(entityManager).unwrap(Session.class);
        verify(session).enableFilter("deletedFilter");
        verify(filter).setParameter("isDeleted", false);
    }

    @Test
    void findByUsername_shouldReturnUser() {
        String username = "testuser";
        JpaUserEntity entity = new JpaUserEntity();
        entity.setId(1L);
        entity.setUsername(username);
        entity.setName("Test User");
        entity.setEmail("test@test.com");
        entity.setPassword("password123");
        entity.setRoleEnum(RoleEnum.PACIENTE);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        when(jpaUserRepositoryGateway.findByUsername(username)).thenReturn(Optional.of(entity));

        Optional<UserDomain> result = userRepository.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(entityManager).unwrap(Session.class);
        verify(session).enableFilter("deletedFilter");
        verify(filter).setParameter("isDeleted", false);
    }

}