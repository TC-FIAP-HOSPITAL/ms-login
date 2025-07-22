package com.fiap.ms.login.domain.model;

public enum Role {
    ADMIN,
    USER;

    public String toAuthority() {
        return "ROLE_" + this.name();
    }
}
