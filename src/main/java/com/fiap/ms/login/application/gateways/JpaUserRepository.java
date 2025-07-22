package com.fiap.ms.login.application.gateways;

import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JpaUserRepository extends JpaRepository<JpaUserEntity, Long>, PagingAndSortingRepository<JpaUserEntity, Long> {
    Optional<JpaUserEntity> findByUsername(String username);
}
