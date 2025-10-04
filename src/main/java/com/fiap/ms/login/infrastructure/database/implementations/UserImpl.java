package com.fiap.ms.login.infrastructure.database.implementations;

import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.database.entities.JpaUserEntity;
import com.fiap.ms.login.infrastructure.database.repositories.JpaUserRepositoryGateway;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.entrypoint.controllers.mappers.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UserImpl implements User {

    private final JpaUserRepositoryGateway jpaUserRepositoryGateway;
    private final EntityManager entityManager;


    public UserImpl(
            JpaUserRepositoryGateway jpaUserRepositoryGateway,
            EntityManager entityManager
    ) {
        this.jpaUserRepositoryGateway = jpaUserRepositoryGateway;
        this.entityManager = entityManager;
    }

    @Override
    public UserDomain save(UserDomain userDomain) {
        JpaUserEntity userEntity = new JpaUserEntity(userDomain);
        JpaUserEntity savedUser = jpaUserRepositoryGateway.save(userEntity);
        return UserMapper.entityToDomain(savedUser);
    }

    @Override
    public UserDomain update(UserDomain userDomain) {
        JpaUserEntity userEntity = jpaUserRepositoryGateway.findById(userDomain.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // update user fields
        userEntity.setName(userDomain.getName());
        userEntity.setEmail(userDomain.getEmail());
        userEntity.setUsername(userDomain.getUsername());
        userEntity.setPassword(userDomain.getPassword());
        userEntity.setRoleEnum(userDomain.getRole());

        JpaUserEntity savedUser = jpaUserRepositoryGateway.save(userEntity);
        return UserMapper.entityToDomain(savedUser);
    }

    @Override
    public List<UserDomain> findAllUsers(Integer page, Integer size) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        Pageable pageable = PageRequest.of(page, size);
        return jpaUserRepositoryGateway.findAll(pageable).stream().map(UserMapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDomain> findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        return jpaUserRepositoryGateway.findById(id).map(UserMapper::entityToDomain);
    }

    @Override
    public Optional<UserDomain> findByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        return jpaUserRepositoryGateway.findByUsername(username).map(UserMapper::entityToDomain);
    }

    @Override
    public void delete(Long id) {
        JpaUserEntity userEntity = jpaUserRepositoryGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userEntity.setDeleted(true);
        jpaUserRepositoryGateway.save(userEntity);
    }
}
