package com.fiap.ms.login.infrastructure.database.repositories;

import com.fiap.ms.login.infrastructure.database.entities.JpaUserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JpaUserRepositoryGateway extends JpaRepository<JpaUserEntity, Long>, PagingAndSortingRepository<JpaUserEntity, Long> {
    Optional<JpaUserEntity> findByUsername(String username);
}
