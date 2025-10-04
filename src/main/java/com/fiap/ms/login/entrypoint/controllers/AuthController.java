package com.fiap.ms.login.entrypoint.controllers;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.entrypoint.controllers.dto.LoginRequestDTO;
import com.fiap.ms.login.entrypoint.controllers.dto.LoginResponse;
import com.fiap.ms.login.infrastructure.config.security.JwtUtil;
import com.fiap.ms.login.infrastructure.config.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/v1")
public class AuthController {

        private final JwtUtil jwtUtil;
        private final AuthenticationManager authenticationManager;

        public AuthController(
                        JwtUtil jwtUtil,
                        AuthenticationManager authenticationManager) {
                this.jwtUtil = jwtUtil;
                this.authenticationManager = authenticationManager;
        }

        @PostMapping("/login")
        public LoginResponse login(@RequestBody LoginRequestDTO loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.username(),
                                                loginRequest.password()));
                MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
                RoleEnum roleEnum = RoleEnum.fromAuthority(userDetails.getAuthorities().iterator().next().getAuthority());
                String token = jwtUtil.generateToken(authentication.getName(), userDetails.getUserId().toString(),
                        roleEnum);
                Claims claims = jwtUtil.extractClaims(token);
                Date expiresAt = jwtUtil.extractExpirationDate(claims);
                String userId = jwtUtil.extractUserId(claims);
                return new LoginResponse(token, authentication.getName(), expiresAt.toString(), userId);
        }
}
