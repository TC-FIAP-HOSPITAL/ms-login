package com.fiap.ms.login.infrastructure.config.init;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    private String adminPassword;

    public AdminUserInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
            ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {

            User adminUser = new User(
                    null,
                    "Administrator",
                    "admin@admin.com",
                    "admin",
                    passwordEncoder.encode(adminPassword),
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
