package com.generator.randomusersgenerator.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@SuppressWarnings("JpaObjectClassSignatureInspection")
@Data
@Getter
@ToString
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String streetAddress;
    private String zipCode;
    private String City;
    private String State;
    private String Country;

    private Address() {
    }

    public Address(String streetAddress, String zipCode, String city, String state, String country) {
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.City = city;
        this.State = state;
        this.Country = country;
    }
}