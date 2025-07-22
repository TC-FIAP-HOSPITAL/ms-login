package com.fiap.ms.login.application.usecase.user;

import com.fiap.ms.login.domain.model.User;
import java.util.Optional;

public interface GetUserByIdUsecase {
    Optional<User> getUserById(String id);
}
