package com.fiap.ms.login.entrypoint.controllers.mappers;

import com.fiap.ms.login.domain.model.Address;
import com.fiap.ms.login.entrypoint.controllers.dto.AddressDto;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaAddressEntity;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private Address address;
    private AddressDto addressDto;
    private JpaAddressEntity addressEntity;
    private JpaUserEntity userEntity;

    @BeforeEach
    void setUp() {
        address = new Address(1L, "Test Street", "123", "Apt 4", "Test City", "TS");

        addressDto = new AddressDto("1", "Test Street", "123", "Apt 4", "Test City", "TS");

        userEntity = new JpaUserEntity();
        userEntity.setId(1L);

        addressEntity = new JpaAddressEntity();
        addressEntity.setId(1L);
        addressEntity.setStreet("Test Street");
        addressEntity.setNumber("123");
        addressEntity.setComplement("Apt 4");
        addressEntity.setCity("Test City");
        addressEntity.setState("TS");
        addressEntity.setUser(userEntity);
    }

    @Test
    void domainToDto_shouldMapCorrectly() {
        AddressDto result = AddressMapper.domainToDto(address);

        assertEquals(address.getId().toString(), result.id());
        assertEquals(address.getStreet(), result.street());
        assertEquals(address.getNumber(), result.number());
        assertEquals(address.getComplement(), result.complement());
        assertEquals(address.getCity(), result.city());
        assertEquals(address.getState(), result.state());
    }

    @Test
    void dtoToDomain_shouldMapCorrectly() {
        Address result = AddressMapper.dtoToDomain(addressDto);

        assertEquals(Long.valueOf(addressDto.id()), result.getId());
        assertEquals(addressDto.street(), result.getStreet());
        assertEquals(addressDto.number(), result.getNumber());
        assertEquals(addressDto.complement(), result.getComplement());
        assertEquals(addressDto.city(), result.getCity());
        assertEquals(addressDto.state(), result.getState());
    }

    @Test
    void dtoToDomain_withNullId_shouldMapCorrectly() {
        AddressDto dtoWithNullId = new AddressDto(null, "Test Street", "123", "Apt 4", "Test City", "TS");

        Address result = AddressMapper.dtoToDomain(dtoWithNullId);

        assertNull(result.getId());
        assertEquals(dtoWithNullId.street(), result.getStreet());
        assertEquals(dtoWithNullId.number(), result.getNumber());
        assertEquals(dtoWithNullId.complement(), result.getComplement());
        assertEquals(dtoWithNullId.city(), result.getCity());
        assertEquals(dtoWithNullId.state(), result.getState());
    }

    @Test
    void entityToDomain_shouldMapCorrectly() {
        Address result = AddressMapper.entityToDomain(addressEntity);

        assertEquals(addressEntity.getId(), result.getId());
        assertEquals(addressEntity.getStreet(), result.getStreet());
        assertEquals(addressEntity.getNumber(), result.getNumber());
        assertEquals(addressEntity.getComplement(), result.getComplement());
        assertEquals(addressEntity.getCity(), result.getCity());
        assertEquals(addressEntity.getState(), result.getState());
    }

    @Test
    void domainToEntity_shouldMapCorrectly() {
        JpaAddressEntity result = AddressMapper.domainToEntity(address, userEntity);

        assertNull(result.getId());
        assertEquals(address.getStreet(), result.getStreet());
        assertEquals(address.getNumber(), result.getNumber());
        assertEquals(address.getComplement(), result.getComplement());
        assertEquals(address.getCity(), result.getCity());
        assertEquals(address.getState(), result.getState());
        assertEquals(userEntity, result.getUser());
    }

    @Test
    void domainToEntity_withNullAddress_shouldReturnNull() {
        JpaAddressEntity result = AddressMapper.domainToEntity(null, userEntity);

        assertNull(result);
    }
}
