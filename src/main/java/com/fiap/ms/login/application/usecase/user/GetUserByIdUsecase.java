package com.fiap.ms.login.application.usecase.user;

import com.fiap.ms.login.domain.model.UserDomain;
import java.util.Optional;

public interface GetUserByIdUsecase {
    Optional<UserDomain> getUserById(String id);
}
