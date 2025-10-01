package com.fiap.ms.login.application.usecase.user.implementation;

import com.fiap.ms.login.application.gateways.PasswordEncoderGateway;
import com.fiap.ms.login.application.usecase.user.UpdateUserUsecase;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserUsecaseImpl implements UpdateUserUsecase {

    private final UserRepository userRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;
    private final SecurityUtil securityUtil;

    public UpdateUserUsecaseImpl(
            UserRepository userRepository,
            PasswordEncoderGateway passwordEncoderGateway,
            SecurityUtil securityUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoderGateway = passwordEncoderGateway;
        this.securityUtil = securityUtil;
    }

  public User updateUser(User user) {
    Long currentUserId = securityUtil.getUserId();
    boolean isAdmin = securityUtil.isAdmin();
    Role currentUserRole = securityUtil.getRole();

    if (!isAdmin) {
      boolean notSameUser = !user.getId().equals(currentUserId);
      boolean roleChanged = !user.getRole().equals(currentUserRole);

      if (notSameUser || roleChanged) {
        throw new AccessDeniedException("Not allowed to update this user");
      }
    }

        user.setPassword(passwordEncoderGateway.encode(user.getPassword()));
        return userRepository.update(user);
    }
}
