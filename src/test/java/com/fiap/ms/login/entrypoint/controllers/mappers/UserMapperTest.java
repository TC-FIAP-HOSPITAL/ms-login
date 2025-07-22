package com.fiap.ms.login.entrypoint.controllers.mappers;

import com.fiap.ms.login.domain.model.Address;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.entrypoint.controllers.dto.AddressDto;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaAddressEntity;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private User user;
    private JpaUserEntity userEntity;
    private UserDtoRequest userDtoRequest;
    private Address address;
    private JpaAddressEntity addressEntity;
    private AddressDto addressDto;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        address = new Address(1L, "Test Street", "123", "Apt 4", "Test City", "TS");
        addressDto = new AddressDto("1", "Test Street", "123", "Apt 4", "Test City", "TS");

        user = new User(1L, "Test User", "test@example.com", "testuser", "password", Role.USER, now, now, address);

        userDtoRequest = new UserDtoRequest("1", "Test User", "test@example.com", "testuser", "password", "USER", addressDto);

        userEntity = new JpaUserEntity();
        userEntity.setId(1L);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");
        userEntity.setUsername("testuser");
        userEntity.setPassword("password");
        userEntity.setRole(Role.USER);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);

        addressEntity = new JpaAddressEntity();
        addressEntity.setId(1L);
        addressEntity.setStreet("Test Street");
        addressEntity.setNumber("123");
        addressEntity.setComplement("Apt 4");
        addressEntity.setCity("Test City");
        addressEntity.setState("TS");
        addressEntity.setUser(userEntity);

        userEntity.setAddress(addressEntity);
    }

    @Test
    void entityToDomain_shouldMapCorrectly() {
        User result = UserMapper.entityToDomain(userEntity);

        assertEquals(userEntity.getId(), result.getId());
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getEmail(), result.getEmail());
        assertEquals(userEntity.getUsername(), result.getUsername());
        assertEquals(userEntity.getPassword(), result.getPassword());
        assertEquals(userEntity.getRole(), result.getRole());
        assertEquals(userEntity.getCreatedAt(), result.getCreatedAt());
        assertEquals(userEntity.getUpdatedAt(), result.getUpdatedAt());

        assertNotNull(result.getAddress());
        assertEquals(addressEntity.getId(), result.getAddress().getId());
        assertEquals(addressEntity.getStreet(), result.getAddress().getStreet());
        assertEquals(addressEntity.getNumber(), result.getAddress().getNumber());
        assertEquals(addressEntity.getComplement(), result.getAddress().getComplement());
        assertEquals(addressEntity.getCity(), result.getAddress().getCity());
        assertEquals(addressEntity.getState(), result.getAddress().getState());
    }

    @Test
    void domainToEntity_shouldMapCorrectly() {
        JpaUserEntity result = UserMapper.domainToEntity(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getRole(), result.getRole());
        assertFalse(result.isDeleted());
    }

    @Test
    void domainToDto_shouldMapCorrectly() {
        UserDtoResponse result = UserMapper.domainToDto(user);

        assertEquals(user.getId().toString(), result.id());
        assertEquals(user.getName(), result.name());
        assertEquals(user.getEmail(), result.email());
        assertEquals(user.getUsername(), result.username());
        assertEquals(user.getRole().toString(), result.role());
        assertEquals(user.getCreatedAt(), result.createdAt());
        assertEquals(user.getUpdatedAt(), result.modifiedAt());

        assertNotNull(result.address());
        assertEquals(address.getId().toString(), result.address().id());
        assertEquals(address.getStreet(), result.address().street());
        assertEquals(address.getNumber(), result.address().number());
        assertEquals(address.getComplement(), result.address().complement());
        assertEquals(address.getCity(), result.address().city());
        assertEquals(address.getState(), result.address().state());
    }

    @Test
    void dtoToDomain_shouldMapCorrectly() {
        User result = UserMapper.dtoToDomain(userDtoRequest);

        assertEquals(Long.valueOf(userDtoRequest.id()), result.getId());
        assertEquals(userDtoRequest.name(), result.getName());
        assertEquals(userDtoRequest.email(), result.getEmail());
        assertEquals(userDtoRequest.username(), result.getUsername());
        assertEquals(userDtoRequest.password(), result.getPassword());
        assertEquals(Role.valueOf(userDtoRequest.role()), result.getRole());

        assertNotNull(result.getAddress());
        assertEquals(Long.valueOf(addressDto.id()), result.getAddress().getId());
        assertEquals(addressDto.street(), result.getAddress().getStreet());
        assertEquals(addressDto.number(), result.getAddress().getNumber());
        assertEquals(addressDto.complement(), result.getAddress().getComplement());
        assertEquals(addressDto.city(), result.getAddress().getCity());
        assertEquals(addressDto.state(), result.getAddress().getState());
    }

    @Test
    void dtoToDomain_withNullId_shouldMapCorrectly() {
        UserDtoRequest dtoWithNullId = new UserDtoRequest(null, "Test User", "test@example.com", "testuser", "password", "USER", addressDto);

        User result = UserMapper.dtoToDomain(dtoWithNullId);

        assertNull(result.getId());
        assertEquals(dtoWithNullId.name(), result.getName());
        assertEquals(dtoWithNullId.email(), result.getEmail());
        assertEquals(dtoWithNullId.username(), result.getUsername());
        assertEquals(dtoWithNullId.password(), result.getPassword());
        assertEquals(Role.valueOf(dtoWithNullId.role()), result.getRole());
    }

    @Test
    void dtoToDomain_address_shouldMapCorrectly() {
        Address result = UserMapper.dtoToDomain(addressDto);

        assertNull(result.getId());
        assertEquals(addressDto.street(), result.getStreet());
        assertEquals(addressDto.number(), result.getNumber());
        assertEquals(addressDto.complement(), result.getComplement());
        assertEquals(addressDto.city(), result.getCity());
        assertEquals(addressDto.state(), result.getState());
    }
}
