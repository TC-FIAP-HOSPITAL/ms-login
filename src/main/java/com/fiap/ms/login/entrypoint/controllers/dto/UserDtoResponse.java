package com.fiap.ms.login.entrypoint.controllers.dto;

import java.time.LocalDateTime;

public record UserDtoResponse(
        String id,
        String name,
        String email,
        String username,
        String role,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
