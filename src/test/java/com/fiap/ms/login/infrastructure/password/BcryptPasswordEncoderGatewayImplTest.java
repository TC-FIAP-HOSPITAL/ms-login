package com.fiap.ms.login.infrastructure.password;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BcryptPasswordEncoderGatewayImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private BcryptPasswordEncoderGatewayImpl bcryptPasswordEncoderGateway;

    @Test
    void encode_shouldReturnEncodedPassword() {
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedPassword";
        
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = bcryptPasswordEncoderGateway.encode(rawPassword);

        assertEquals(encodedPassword, result);
    }
}