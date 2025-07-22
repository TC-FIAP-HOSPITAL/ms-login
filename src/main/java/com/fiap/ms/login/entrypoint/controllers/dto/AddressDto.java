package com.fiap.ms.login.entrypoint.controllers.dto;

public record AddressDto(
        String id,
        String street,
        String number,
        String complement,
        String city,
        String state
) {
}