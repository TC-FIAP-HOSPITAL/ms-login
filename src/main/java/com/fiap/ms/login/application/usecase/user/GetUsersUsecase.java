package com.fiap.ms.login.application.usecase.user;

import com.fiap.ms.login.domain.model.User;
import java.util.List;

public interface GetUsersUsecase {
    List<User> getUsers(Integer page, Integer size);
}
