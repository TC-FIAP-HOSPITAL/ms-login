package com.fiap.ms.login.entrypoint.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.entrypoint.controllers.dto.LoginRequestDTO;
import com.fiap.ms.login.entrypoint.controllers.dto.LoginResponse;
import com.fiap.ms.login.infrastructure.config.security.JwtUtil;
import com.fiap.ms.login.infrastructure.config.security.MyUserDetails;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    private Authentication authentication;
    private MyUserDetails userDetails;
    private Claims claims;
    private Date expirationDate;

    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");

        userDetails = mock(MyUserDetails.class);
        when(userDetails.getUserId()).thenReturn(1L);
        when(userDetails.getAuthorities())
                .thenAnswer(invocation -> Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        when(authentication.getPrincipal()).thenReturn(userDetails);

        claims = mock(Claims.class);

        expirationDate = new Date(System.currentTimeMillis() + 3600000);
    }

    @Test
    void login_shouldAuthenticateAndReturnToken() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("testuser", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userDetails.getEmail()).thenReturn("test@example.com");
        when(jwtUtil.generateToken(eq("testuser"), eq("1"), any(RoleEnum.class), eq("test@example.com")))
                .thenReturn("test-token");
        when(jwtUtil.extractClaims(anyString())).thenReturn(claims);
        when(jwtUtil.extractExpirationDate(claims)).thenReturn(expirationDate);
        when(jwtUtil.extractUserId(claims)).thenReturn("1");

        LoginResponse response = authController.login(loginRequest);

        assertNotNull(response);
        assertEquals("test-token", response.token());
        assertEquals("testuser", response.username());
        assertEquals(expirationDate.toString(), response.expiresAt());
        assertEquals("1", response.userId());
    }
}
