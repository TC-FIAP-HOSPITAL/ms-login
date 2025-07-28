package com.fiap.ms.login.infrastructure.password;

import com.fiap.ms.login.application.gateways.PasswordEncoderGateway;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoderGatewayImpl implements PasswordEncoderGateway {

    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public BcryptPasswordEncoderGatewayImpl(org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence password) {
        return passwordEncoder.encode(password);
    }
}
