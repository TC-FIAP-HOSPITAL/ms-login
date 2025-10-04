package com.fiap.ms.login.application.gateways;

public interface PasswordEncoder {
    String encode(CharSequence password);
}
