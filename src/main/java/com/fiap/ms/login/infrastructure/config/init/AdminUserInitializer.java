package com.fiap.ms.login.infrastructure.config.init;

import com.fiap.ms.login.application.gateways.PasswordEncoderGateway;
import com.fiap.ms.login.domain.model.Address;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.domain.model.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;

    @Value("${admin.password}")
    private String adminPassword;

    public AdminUserInitializer(
            UserRepository userRepository,
            PasswordEncoderGateway passwordEncoderGateway
            ) {
        this.userRepository = userRepository;
        this.passwordEncoderGateway = passwordEncoderGateway;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {

            User adminUser = new User(
                    null,
                    "Administrator",
                    "admin@admin.com",
                    "admin",
                    passwordEncoderGateway.encode(adminPassword),
                    Role.ADMIN,
                    new Address(
                            null,
                            "Baker Street",
                            "1337",
                            "nil",
                            "London",
                            "GL"

                    )
            );

            userRepository.save(adminUser);
            log.info("admin user created.");
        }
    }
}
