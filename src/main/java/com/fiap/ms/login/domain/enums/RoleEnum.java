package com.fiap.ms.login.domain.enums;

public enum RoleEnum {
  ADMIN,
  MEDICO,
  ENFERMEIRO,
  PACIENTE;

  public String toAuthority() {
    return "ROLE_" + this.name();
  }

  public static RoleEnum fromAuthority(String authority) {
    String name = authority.startsWith("ROLE_") ? authority.substring(5) : authority;
    return RoleEnum.valueOf(name);
  }
}
