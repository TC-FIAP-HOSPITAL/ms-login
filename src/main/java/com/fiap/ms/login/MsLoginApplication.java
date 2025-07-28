package com.fiap.ms.login;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class MsLoginApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MsLoginApplication.class).registerShutdownHook(true).run(args);
    }
}
