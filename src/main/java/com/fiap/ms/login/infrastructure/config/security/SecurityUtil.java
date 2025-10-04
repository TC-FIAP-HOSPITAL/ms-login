package com.fiap.ms.login.infrastructure.config.security;

import com.fiap.ms.login.domain.enums.RoleEnum;
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
                .anyMatch(a -> a.getAuthority().equals(RoleEnum.ADMIN.toAuthority()));
    }

    public RoleEnum getRole() {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      if (auth == null || auth.getAuthorities() == null) {
        return RoleEnum.PACIENTE;
      }

      return auth.getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority) // e.g. "ROLE_ADMIN"
          .map(RoleEnum::fromAuthority)            // -> Role.ADMIN
          .findFirst().orElse(RoleEnum.PACIENTE);
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
