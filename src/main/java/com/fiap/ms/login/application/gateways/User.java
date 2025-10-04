package com.fiap.ms.login.application.gateways;

import com.fiap.ms.login.domain.model.UserDomain;

import java.util.List;
import java.util.Optional;

public interface User {
    UserDomain save(UserDomain userDomain);
    UserDomain update(UserDomain userDomain);
    List<UserDomain> findAllUsers(Integer page, Integer size);
    Optional<UserDomain> findById(Long id);
    Optional<UserDomain> findByUsername(String username);
    void delete(Long id);
}
