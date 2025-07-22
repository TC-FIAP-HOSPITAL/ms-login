package com.fiap.ms.login.domain.model;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    User update(User user);
    List<User> findAllUsers(Integer page, Integer size);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void delete(Long id);
}
