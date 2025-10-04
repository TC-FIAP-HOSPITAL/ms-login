package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.GetUserByIdUsecase;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

public class GetUserByIdUsecaseImpl implements GetUserByIdUsecase {

    private final User user;
    private final SecurityUtil securityUtil;

    public GetUserByIdUsecaseImpl(
            User user,
            SecurityUtil securityUtil
    ) {
        this.user = user;
        this.securityUtil = securityUtil;
    }

    @Override
    public Optional<UserDomain> getUserById(String id) {
            Long userId = Long.valueOf(id);
            
            // Allow access if user is requesting their own data OR if user is admin
            if (!userId.equals(securityUtil.getUserId()) && !securityUtil.isAdmin()) {
                throw new AccessDeniedException("Access denied: You can only access your own data unless you are an admin");
            }
            return user.findById(userId);
    }
}
