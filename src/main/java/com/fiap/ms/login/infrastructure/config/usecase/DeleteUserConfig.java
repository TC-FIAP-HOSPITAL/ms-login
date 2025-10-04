package com.fiap.ms.login.infrastructure.config.usecase;

import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.implementation.DeleteUserUsecaseImpl;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteUserConfig {

    @Bean
    public DeleteUserUsecaseImpl deleteUserUsecase(User user,
                                                   SecurityUtil securityUtil) {
        return new DeleteUserUsecaseImpl(user, securityUtil);
    }
}

