package com.fiap.ms.login.infrastructure.config.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class SecurityExceptionHandlerConfigTest {

    private SecurityExceptionHandlerConfig config;
    private ObjectMapper objectMapper;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws Exception {
        config = new SecurityExceptionHandlerConfig();
        objectMapper = new ObjectMapper();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);
    }

    @Test
    void authenticationEntryPoint_shouldReturnUnauthorizedJson() throws Exception {
        AuthenticationEntryPoint entryPoint = config.authenticationEntryPoint(objectMapper);

        when(request.getRequestURI()).thenReturn("/secure");
        when(response.getWriter()).thenReturn(writer);

        assertDoesNotThrow(() -> entryPoint.commence(request, response, null));

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(captor.capture());

        String body = captor.getValue();
        assertEquals(
                "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Full authentication is required to access this resource\",\"path\":\"/secure\"}",
                body);
    }

    @Test
    void accessDeniedHandler_shouldReturnForbiddenJson() throws Exception {
        AccessDeniedHandler handler = config.accessDeniedHandler(objectMapper);

        when(request.getRequestURI()).thenReturn("/secure");
        when(response.getWriter()).thenReturn(writer);

        assertDoesNotThrow(() -> handler.handle(request, response, null));

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response).setContentType("application/json");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(captor.capture());

        String body = captor.getValue();
        assertEquals(
                "{\"status\":403,\"error\":\"Forbidden\",\"message\":\"You do not have permission to perform this action\",\"path\":\"/secure\"}",
                body);
    }
}
