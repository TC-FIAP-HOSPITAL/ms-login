package com.fiap.ms.login.infrastructure.config.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fiap.ms.login.domain.enums.RoleEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String TEST_SECRET = "testSecretKeyWithAtLeast256BitsForHS256Algorithm";
    private final String TEST_USERNAME = "testuser";
    private final String TEST_USER_ID = "1";
    private final RoleEnum TEST_ROLEEnum = RoleEnum.ADMIN;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", TEST_SECRET);
    }

    @Test
    void generateToken_shouldCreateValidToken() {
        // Act
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void extractClaims_shouldReturnValidClaims() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);

        // Act
        Claims claims = jwtUtil.extractClaims(token);

        // Assert
        assertNotNull(claims);
        assertEquals(TEST_USERNAME, claims.getSubject());
        assertEquals(TEST_USER_ID, claims.get("userId"));
        assertEquals(TEST_ROLEEnum.name(), claims.get("role"));
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);
        Claims claims = jwtUtil.extractClaims(token);

        // Act
        String username = jwtUtil.extractUsername(claims);

        // Assert
        assertEquals(TEST_USERNAME, username);
    }

    @Test
    void extractUserId_shouldReturnCorrectUserId() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);
        Claims claims = jwtUtil.extractClaims(token);

        // Act
        String userId = jwtUtil.extractUserId(claims);

        // Assert
        assertEquals(TEST_USER_ID, userId);
    }

    @Test
    void extractExpirationDate_shouldReturnCorrectExpiration() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);
        Claims claims = jwtUtil.extractClaims(token);

        // Act
        Date expirationDate = jwtUtil.extractExpirationDate(claims);

        // Assert
        assertNotNull(expirationDate);
    }

    @Test
    void extractExpirationDateFromToken_shouldReturnCorrectExpiration() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);

        // Act
        Date expirationDate = jwtUtil.extractExpirationDateFromToken(token);

        // Assert
        assertNotNull(expirationDate);
    }

    @Test
    void validateToken_withValidToken_shouldReturnTrue() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);

        // Act & Assert
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void validateToken_withInvalidToken_shouldReturnFalse() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act & Assert
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    void validateToken_withExpiredToken_shouldReturnFalse() {
        Key key = (Key) ReflectionTestUtils.invokeMethod(jwtUtil, "getSigningKey");
        Date issued = new Date(System.currentTimeMillis() - 5000);
        Date past = new Date(System.currentTimeMillis() - 1000);
        String expiredToken = Jwts.builder()
                .setSubject(TEST_USERNAME)
                .claim("userId", TEST_USER_ID)
                .claim("role", TEST_ROLEEnum.name())
                .setIssuedAt(issued)
                .setExpiration(past)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(jwtUtil.validateToken(expiredToken));
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_USER_ID, TEST_ROLEEnum);
        Claims claims = jwtUtil.extractClaims(token);

        // Act
        RoleEnum roleEnum = jwtUtil.extractRole(claims);

        // Assert
        assertEquals(TEST_ROLEEnum, roleEnum);
    }
}