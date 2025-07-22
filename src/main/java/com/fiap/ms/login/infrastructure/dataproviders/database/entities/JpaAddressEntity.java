package com.fiap.ms.login.infrastructure.dataproviders.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="address")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JpaAddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;

    @OneToOne
    @JoinColumn(name="user_id")
    private JpaUserEntity user;
}