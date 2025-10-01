package com.fiap.ms.login.infrastructure.config.security;

import com.fiap.ms.login.domain.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityUtilTest {

    @InjectMocks
    private SecurityUtil securityUtil;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private MockedStatic<SecurityContextHolder> mockedSecurityContextHolder;

    @BeforeEach
    void setUp() {
        mockedSecurityContextHolder = Mockito.mockStatic(SecurityContextHolder.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
    }

    @AfterEach
    void tearDown() {
        mockedSecurityContextHolder.close();
    }

    @Test
    void getAuthentication_shouldReturnAuthenticationFromContext() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Act
        Authentication result = securityUtil.getAuthentication();

        // Assert
        assertEquals(authentication, result);
    }

    @Test
    void getCurrentUsername_withAuthenticatedUser_shouldReturnUsername() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");

        // Act
        String result = securityUtil.getCurrentUsername();

        // Assert
        assertEquals("testuser", result);
    }

    @Test
    void getCurrentUsername_withNoAuthentication_shouldReturnNull() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        String result = securityUtil.getCurrentUsername();

        // Assert
        assertNull(result);
    }

    @Test
    void getJwtToken_withAuthenticatedUser_shouldReturnToken() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getCredentials()).thenReturn("test-token");

        // Act
        String result = securityUtil.getJwtToken();

        // Assert
        assertEquals("test-token", result);
    }

    @Test
    void getJwtToken_withNoAuthentication_shouldReturnNull() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        String result = securityUtil.getJwtToken();

        // Assert
        assertNull(result);
    }

    @Test
    void isAdmin_withAdminUser_shouldReturnTrue() {
        // Arrange
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(Role.ADMIN.toAuthority());
        Authentication adminAuth = new UsernamePasswordAuthenticationToken(
                "admin", "password", Collections.singletonList(adminAuthority));
        when(securityContext.getAuthentication()).thenReturn(adminAuth);

        // Act
        Boolean result = securityUtil.isAdmin();

        // Assert
        assertTrue(result);
    }

    @Test
    void isAdmin_withNonAdminUser_shouldReturnFalse() {
        // Arrange
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority(Role.PACIENTE.toAuthority());
        Authentication userAuth = new UsernamePasswordAuthenticationToken(
                "user", "password", Collections.singletonList(userAuthority));
        when(securityContext.getAuthentication()).thenReturn(userAuth);

        // Act
        Boolean result = securityUtil.isAdmin();

        // Assert
        assertFalse(result);
    }

    @Test
    void getUserId_withAuthenticatedUserDetails_shouldReturnUserId() {
        // Arrange
        MyUserDetails userDetails = mock(MyUserDetails.class);
        when(userDetails.getUserId()).thenReturn(1L);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        Long result = securityUtil.getUserId();

        // Assert
        assertEquals(1L, result);
    }

    @Test
    void getUserId_withNonUserDetailsPrincipal_shouldReturnNull() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("not-a-user-details");

        // Act
        Long result = securityUtil.getUserId();

        // Assert
        assertNull(result);
    }

    @Test
    void getUserId_withNoAuthentication_shouldReturnNull() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        Long result = securityUtil.getUserId();

        // Assert
        assertNull(result);
    }

    @Test
    void getUserId_withNotAuthenticatedUser_shouldReturnNull() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act
        Long result = securityUtil.getUserId();

        // Assert
        assertNull(result);
    }
}
