package com.fiap.ms.login.infrastructure.config.init;

import com.fiap.ms.login.application.gateways.PasswordEncoder;
import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.application.gateways.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminUserInitializer {

    private final User user;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    private String adminPassword;

    public AdminUserInitializer(
            User user,
            PasswordEncoder passwordEncoder
            ) {
        this.user = user;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (user.findByUsername("admin").isEmpty()) {

            UserDomain adminUserDomain = new UserDomain(
                    null,
                    "Administrator",
                    "admin@admin.com",
                    "admin",
                    passwordEncoder.encode(adminPassword),
                    RoleEnum.ADMIN
            );

            user.save(adminUserDomain);
            log.info("admin user created.");
        }
    }
}
