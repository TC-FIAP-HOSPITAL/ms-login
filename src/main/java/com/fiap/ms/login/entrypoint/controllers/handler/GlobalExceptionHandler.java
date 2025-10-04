package com.fiap.ms.login.entrypoint.controllers.handler;

import com.fiap.ms.login.domain.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        ApiError error = new ApiError(
                403,
                "Forbidden",
                ex.getMessage().isEmpty() ? "You do not have permission to perform this action." : ex.getMessage(),
                req.getRequestURI()
        );
        log.warn("Access denied: {}", error);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(AuthenticationException ex, HttpServletRequest req) {
        ApiError error = new ApiError(
                401,
                "Unauthorized",
                ex.getMessage().isEmpty() ? "Full authentication is required to access this resource." : ex.getMessage(),
                req.getRequestURI()
        );
        log.info("Authentication failed: {}", error);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req) {
        ApiError error = new ApiError(
                500,
                "Internal Server Error",
                ex.getMessage(),
                req.getRequestURI()
        );
        log.error("Unexpected error: {}", error);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleInvalidRole(Exception ex, HttpServletRequest req) {
        ApiError error = new ApiError(
                400,
                "Bad Request",
                ex.getMessage(),
                req.getRequestURI()
        );
        log.warn("Bad request: {}", error);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleJpaIntegrity(Exception ex, HttpServletRequest req) {
        ApiError error = new ApiError(
                409,
                "Conflict",
                "A resource with the same unique value already exists.",
                req.getRequestURI()
        );
        log.warn("Conflict: {} - {}", error, ex.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(Exception ex, HttpServletRequest req) {
        ApiError error = new ApiError(
                404,
                "Not Found",
                ex.getMessage(),
                req.getRequestURI()
        );
        log.warn("User Not Found: {}", error);
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
