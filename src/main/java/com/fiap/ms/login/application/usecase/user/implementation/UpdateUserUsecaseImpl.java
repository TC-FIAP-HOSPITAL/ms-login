package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.usecase.user.UpdateUserUsecase;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserUsecaseImpl implements UpdateUserUsecase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    public UpdateUserUsecaseImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SecurityUtil securityUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
    }

    public User updateUser(User user) {
        boolean notSameUser = !user.getId().equals(securityUtil.getUserId());
        boolean notAdmin = !securityUtil.isAdmin();

        if (notSameUser && notAdmin) {
            throw new AccessDeniedException(null);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.update(user);
    }
}
