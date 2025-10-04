package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.DeleteUserUsecase;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;

public class DeleteUserUsecaseImpl implements DeleteUserUsecase {

    private final User user;
    private final SecurityUtil securityUtil;

    public DeleteUserUsecaseImpl(
            User user,
            SecurityUtil securityUtil
    ) {
        this.user = user;
        this.securityUtil = securityUtil;
    }

    @Override
    public void deleteUser(String userId) {
        Long id = Long.valueOf(userId);
        boolean notSameUser = !id.equals(securityUtil.getUserId());
        boolean notAdmin = !securityUtil.isAdmin();

        if (notSameUser && notAdmin) {
            throw new AccessDeniedException(null);
        }

        user.delete(id);
    }
}
