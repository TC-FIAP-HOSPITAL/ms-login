package com.fiap.ms.login.infrastructure.config.security;

import com.fiap.ms.login.domain.model.Role;
import java.util.Collection;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUsername() {
        Authentication auth = getAuthentication();
        return auth != null ? auth.getName() : null;
    }

    public String getJwtToken() {
        Authentication auth = getAuthentication();
        return auth != null ? auth.getCredentials().toString() : null;
    }

    public Boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.toAuthority()));
    }

    public Role getRole() {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      if (auth == null || auth.getAuthorities() == null) {
        return Role.PACIENTE;
      }

      return auth.getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority) // e.g. "ROLE_ADMIN"
          .map(Role::fromAuthority)            // -> Role.ADMIN
          .findFirst().orElse(Role.PACIENTE);
    }

    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof MyUserDetails myUserDetails) {
            return myUserDetails.getUserId();
        }
        return null;
    }
}
