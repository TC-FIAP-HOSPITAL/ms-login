package com.fiap.ms.login.application.usecase.user;

import com.fiap.ms.login.domain.model.UserDomain;

import java.util.List;

public interface GetUsersUsecase {
    List<UserDomain> getUsers(Integer page, Integer size);
}
