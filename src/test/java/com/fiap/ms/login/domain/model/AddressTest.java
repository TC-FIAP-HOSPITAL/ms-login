package com.fiap.ms.login.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void defaultConstructor_shouldCreateEmptyAddress() {
        Address address = new Address();
        
        assertNull(address.getId());
        assertNull(address.getStreet());
        assertNull(address.getNumber());
        assertNull(address.getComplement());
        assertNull(address.getCity());
        assertNull(address.getState());
    }

    @Test
    void constructorWithoutId_shouldSetFields() {
        String street = "Main Street";
        String number = "123";
        String complement = "Apt 4B";
        String city = "São Paulo";
        String state = "SP";

        Address address = new Address(street, number, complement, city, state);

        assertNull(address.getId());
        assertEquals(street, address.getStreet());
        assertEquals(number, address.getNumber());
        assertEquals(complement, address.getComplement());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
    }

    @Test
    void constructorWithId_shouldSetAllFields() {
        Long id = 1L;
        String street = "Main Street";
        String number = "123";
        String complement = "Apt 4B";
        String city = "São Paulo";
        String state = "SP";

        Address address = new Address(id, street, number, complement, city, state);

        assertEquals(id, address.getId());
        assertEquals(street, address.getStreet());
        assertEquals(number, address.getNumber());
        assertEquals(complement, address.getComplement());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        Address address = new Address();
        Long id = 2L;
        String street = "Updated Street";
        String number = "456";
        String complement = "Suite 10";
        String city = "Rio de Janeiro";
        String state = "RJ";

        address.setId(id);
        address.setStreet(street);
        address.setNumber(number);
        address.setComplement(complement);
        address.setCity(city);
        address.setState(state);

        assertEquals(id, address.getId());
        assertEquals(street, address.getStreet());
        assertEquals(number, address.getNumber());
        assertEquals(complement, address.getComplement());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
    }
}