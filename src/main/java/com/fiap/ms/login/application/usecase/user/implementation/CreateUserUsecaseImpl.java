package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.CreateUserUsecase;
import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;

public class CreateUserUsecaseImpl implements CreateUserUsecase {

    private final User user;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    public CreateUserUsecaseImpl(
            User user,
            PasswordEncoder passwordEncoder,
            SecurityUtil securityUtil
    ) {
        this.user = user;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
    }

    @Override
    public UserDomain createUser(UserDomain userDomain) {
        boolean isAdmin = securityUtil.isAdmin();
        RoleEnum roleEnumUser = userDomain.getRole();

        if (roleEnumUser != RoleEnum.PACIENTE && !isAdmin) {
            throw new AccessDeniedException(null);
        }

        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return user.save(userDomain);
    }
}
