package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.usecase.user.GetUsersUsecase;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetUsersUsecaseImpl implements GetUsersUsecase {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public GetUsersUsecaseImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SecurityUtil securityUtil
    ) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    public List<User> getUsers(Integer page, Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!securityUtil.isAdmin()) {
            throw new AccessDeniedException(null);
        };
        return userRepository.findAllUsers(page, size);
    }
}
