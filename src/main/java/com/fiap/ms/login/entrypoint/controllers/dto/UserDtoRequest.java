package com.fiap.ms.login.entrypoint.controllers.dto;

public record UserDtoRequest(
        String id,
        String name,
        String email,
        String username,
        String password,
        String role
) {
}
