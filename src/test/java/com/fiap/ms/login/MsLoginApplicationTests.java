package com.fiap.ms.login;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("h2")
@TestPropertySource(locations = "classpath:application-h2.properties")
class MsLoginApplicationTests {

    @Test
    void contextLoads() {
    }
}
