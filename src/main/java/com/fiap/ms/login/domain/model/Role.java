package com.fiap.ms.login.domain.model;

public enum Role {
  ADMIN,
  MEDICO,
  ENFERMEIRO,
  PACIENTE;

  public String toAuthority() {
    return "ROLE_" + this.name();
  }

  public static Role fromAuthority(String authority) {
    String name = authority.startsWith("ROLE_") ? authority.substring(5) : authority;
    return Role.valueOf(name);
  }
}
