package com.fiap.ms.login.entrypoint.controllers.handler;

import com.fiap.ms.login.domain.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleAccessDenied_shouldReturn403() {
        AccessDeniedException ex = new AccessDeniedException("Access denied");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleAccessDenied(ex, request);

        assertEquals(403, response.getStatusCode().value());
        assertEquals("Access denied", response.getBody().getMessage());
    }

    @Test
    void handleAuthentication_shouldReturn401() {
        AuthenticationException ex = new AuthenticationException("Authentication failed") {};
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleAuthentication(ex, request);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Authentication failed", response.getBody().getMessage());
    }

    @Test
    void handleAll_shouldReturn500() {
        Exception ex = new Exception("Internal error");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleAll(ex, request);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Internal error", response.getBody().getMessage());
    }

    @Test
    void handleInvalidRole_shouldReturn400() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid role");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleInvalidRole(ex, request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid role", response.getBody().getMessage());
    }

    @Test
    void handleJpaIntegrity_shouldReturn409() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Constraint violation");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleJpaIntegrity(ex, request);

        assertEquals(409, response.getStatusCode().value());
        assertEquals("A resource with the same unique value already exists.", response.getBody().getMessage());
    }

    @Test
    void handleUserNotFound_shouldReturn404() {
        UserNotFoundException ex = new UserNotFoundException("User not found");
        when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ApiError> response = globalExceptionHandler.handleUserNotFound(ex, request);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("User not found", response.getBody().getMessage());
    }
}