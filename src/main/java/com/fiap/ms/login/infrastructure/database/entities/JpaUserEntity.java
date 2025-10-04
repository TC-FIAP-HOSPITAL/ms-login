package com.fiap.ms.login.infrastructure.database.entities;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "tb_users")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = boolean.class))
@Filter(name = "deletedFilter", condition = "is_deleted = :isDeleted")
public class JpaUserEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;
    private String password;
    private RoleEnum roleEnum;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name= "is_deleted")
    private boolean isDeleted;
    private LocalDateTime deletedAt;

    public JpaUserEntity(UserDomain userDomain) {
        this.id = userDomain.getId();
        this.name = userDomain.getName();
        this.email = userDomain.getEmail();
        this.username = userDomain.getUsername();
        this.password = userDomain.getPassword();
        this.roleEnum = userDomain.getRole();
        this.isDeleted = false;
    }
}