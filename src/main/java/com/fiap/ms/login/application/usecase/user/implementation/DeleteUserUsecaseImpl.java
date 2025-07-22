package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.usecase.user.DeleteUserUsecase;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUsecaseImpl implements DeleteUserUsecase {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public DeleteUserUsecaseImpl(
            UserRepository userRepository,
            SecurityUtil securityUtil
    ) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    public void deleteUser(String userId) {
        Long id = Long.valueOf(userId);
        boolean notSameUser = !id.equals(securityUtil.getUserId());
        boolean notAdmin = !securityUtil.isAdmin();

        if (notSameUser && notAdmin) {
            throw new AccessDeniedException(null);
        }
        userRepository.delete(id);
    }
}
