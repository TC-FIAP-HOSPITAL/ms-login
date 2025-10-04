package com.fiap.ms.login.infrastructure.config.swagger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.swagger.v3.oas.models.OpenAPI;

class SwaggerConfigTest {

    @Test
    void apiInfo_shouldReturnConfiguredOpenApi() {
        SwaggerConfig config = new SwaggerConfig();

        OpenAPI openAPI = config.apiInfo();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("MS-Login API", openAPI.getInfo().getTitle());
        assertEquals("API documentation login microservice", openAPI.getInfo().getDescription());
        assertEquals("v1.0", openAPI.getInfo().getVersion());
    }
}
