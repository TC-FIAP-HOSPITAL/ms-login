package com.fiap.ms.login.entrypoint.controllers.mappers;

import com.fiap.ms.login.domain.model.Address;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaAddressEntity;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import com.fiap.ms.login.entrypoint.controllers.dto.AddressDto;

public class AddressMapper {
    public static AddressDto domainToDto(Address address) {
        return new AddressDto(
                address.getId().toString(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getCity(),
                address.getState()
        );
    }

    public static Address dtoToDomain(AddressDto addressDto) {
        return new Address(
                addressDto.id() == null ? null : Long.valueOf(addressDto.id()),
                addressDto.street(),
                addressDto.number(),
                addressDto.complement(),
                addressDto.city(),
                addressDto.state()
        );
    }

    public static Address entityToDomain(JpaAddressEntity addressEntity) {
        return new Address(
                addressEntity.getId(),
                addressEntity.getStreet(),
                addressEntity.getNumber(),
                addressEntity.getComplement(),
                addressEntity.getCity(),
                addressEntity.getState()
        );
    }

    public static JpaAddressEntity domainToEntity(Address address, JpaUserEntity userEntity) {
        if (address == null) return null;
        JpaAddressEntity entity = new JpaAddressEntity();
        entity.setStreet(address.getStreet());
        entity.setNumber(address.getNumber());
        entity.setComplement(address.getComplement());
        entity.setCity(address.getCity());
        entity.setState(address.getState());
        entity.setUser(userEntity);
        return entity;
    }
}
