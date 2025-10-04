package com.fiap.ms.login.application.usecase.user;

import com.fiap.ms.login.domain.model.UserDomain;

public interface UpdateUserUsecase {
    UserDomain updateUser(UserDomain userDomain);
}
