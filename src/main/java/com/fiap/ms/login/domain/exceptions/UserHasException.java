package com.fiap.ms.login.domain.exceptions;

public class UserHasException extends RuntimeException {
    public UserHasException(String message) {
        super(message);
    }
}
