package com.fiap.ms.login.infrastructure.dataproviders.database.implementations;

import com.fiap.ms.login.application.gateways.JpaUserRepositoryGateway;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private JpaUserRepositoryGateway jpaUserRepositoryGateway;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Filter filter;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    void save_shouldSaveUser() {
        User user = new User();
        user.setName("Test User");
        JpaUserEntity entity = new JpaUserEntity(user);
        when(jpaUserRepositoryGateway.save(any(JpaUserEntity.class))).thenReturn(entity);

        User result = userRepository.save(user);

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

}