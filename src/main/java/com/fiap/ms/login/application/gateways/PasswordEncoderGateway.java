package com.fiap.ms.login.application.gateways;

public interface PasswordEncoderGateway {
    String encode(CharSequence password);
}
