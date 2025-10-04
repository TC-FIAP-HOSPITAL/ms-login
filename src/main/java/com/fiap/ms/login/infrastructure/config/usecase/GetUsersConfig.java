package com.fiap.ms.login.infrastructure.config.usecase;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.implementation.GetUsersUsecaseImpl;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GetUsersConfig {

    @Bean
    public GetUsersUsecaseImpl getUsersUsecase(User user,
                                               PasswordEncoder passwordEncoder,
                                               SecurityUtil securityUtil) {
        return new GetUsersUsecaseImpl(user, passwordEncoder, securityUtil);
    }
}
