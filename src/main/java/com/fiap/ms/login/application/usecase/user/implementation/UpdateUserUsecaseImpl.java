package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.UpdateUserUsecase;
import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;

public class UpdateUserUsecaseImpl implements UpdateUserUsecase {

    private final User user;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    public UpdateUserUsecaseImpl(
            User user,
            PasswordEncoder passwordEncoder,
            SecurityUtil securityUtil
    ) {
        this.user = user;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
    }

    @Override
    public UserDomain updateUser(UserDomain userDomain) {
        Long currentUserId = securityUtil.getUserId();
        boolean isAdmin = securityUtil.isAdmin();
        RoleEnum currentUserRoleEnum = securityUtil.getRole();

        if (!isAdmin) {
            boolean notSameUser = !userDomain.getId().equals(currentUserId);
            boolean roleChanged = !userDomain.getRole().equals(currentUserRoleEnum);

            if (notSameUser || roleChanged) {
                throw new AccessDeniedException("Not allowed to update this user");
          }
        }

        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return user.update(userDomain);
    }
}
