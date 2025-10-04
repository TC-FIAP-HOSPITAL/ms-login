package com.fiap.ms.login.infrastructure.config.usecase;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.application.gateways.User;
import com.fiap.ms.login.application.usecase.user.implementation.CreateUserUsecaseImpl;
import com.fiap.ms.login.infrastructure.config.security.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateUserConfig {

    @Bean
    public CreateUserUsecaseImpl createUserUsecase(User user,
                                                   PasswordEncoder passwordEncoder,
                                                   SecurityUtil securityUtil) {
        return new CreateUserUsecaseImpl(user, passwordEncoder, securityUtil);
    }
}