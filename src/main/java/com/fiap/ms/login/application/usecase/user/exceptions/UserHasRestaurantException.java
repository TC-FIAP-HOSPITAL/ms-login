package com.fiap.ms.login.application.usecase.user.exceptions;

public class UserHasRestaurantException extends RuntimeException {
    public UserHasRestaurantException(String message) {
        super(message);
    }
}
