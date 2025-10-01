package com.fiap.ms.login.application.usecase.user.exceptions;

public class UserHasException extends RuntimeException {
    public UserHasException(String message) {
        super(message);
    }
}
