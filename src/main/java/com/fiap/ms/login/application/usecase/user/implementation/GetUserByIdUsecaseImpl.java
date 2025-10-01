package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.usecase.user.GetUserByIdUsecase;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUsecaseImpl implements GetUserByIdUsecase {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public GetUserByIdUsecaseImpl(
            UserRepository userRepository,
            SecurityUtil securityUtil
    ) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    public Optional<User> getUserById(String id) {
            Long userId = Long.valueOf(id);
            
            // Allow access if user is requesting their own data OR if user is admin
            if (!userId.equals(securityUtil.getUserId()) && !securityUtil.isAdmin()) {
                throw new AccessDeniedException("Access denied: You can only access your own data unless you are an admin");
            }
            return userRepository.findById(userId);
    }
}
