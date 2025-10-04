package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.GetUsersUsecase;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class GetUsersUsecaseImpl implements GetUsersUsecase {

    private final User user;
    private final SecurityUtil securityUtil;

    public GetUsersUsecaseImpl(
            User user,
            PasswordEncoder passwordEncoder,
            SecurityUtil securityUtil
    ) {
        this.user = user;
        this.securityUtil = securityUtil;
    }

    @Override
    public List<UserDomain> getUsers(Integer page, Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!securityUtil.isAdmin()) {
            throw new AccessDeniedException(null);
        };
        return user.findAllUsers(page, size);
    }
}
