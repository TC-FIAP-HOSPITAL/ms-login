package com.fiap.ms.login.domain.model;

import com.fiap.ms.login.domain.enums.RoleEnum;

import java.time.LocalDateTime;

public class UserDomain {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private RoleEnum roleEnum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDomain() {
    }

    public UserDomain(Long id, String name, String email, String username, String password, RoleEnum roleEnum) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    public UserDomain(Long id, String name, String email, String username, String password, RoleEnum roleEnum, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleEnum = roleEnum;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return roleEnum;
    }

    public void setRole(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
