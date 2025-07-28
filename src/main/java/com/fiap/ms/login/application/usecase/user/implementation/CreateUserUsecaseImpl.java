package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoderGateway;
import com.fiap.ms.login.application.usecase.user.CreateUserUsecase;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecaseImpl implements CreateUserUsecase {

    private final UserRepository userRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;
    private final SecurityUtil securityUtil;

    public CreateUserUsecaseImpl(
            UserRepository userRepository,
            PasswordEncoderGateway passwordEncoderGateway,
            SecurityUtil securityUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoderGateway = passwordEncoderGateway;
        this.securityUtil = securityUtil;
    }

    public User createUser(User user) {
        boolean isAdmin = securityUtil.isAdmin();
        boolean roleAdmin = user.getRole().equals(Role.ADMIN);

        if (roleAdmin && !isAdmin) {
            throw new AccessDeniedException(null);
        }

        user.setPassword(passwordEncoderGateway.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
