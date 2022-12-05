package com.generator.randomusersgenerator.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Getter
@Setter
@ToString
public class Address {
    @Id
    @GeneratedValue
    private Long addressId;
    @NotNull
    @Size(min = 5)
    private String streetAddress;
    @NotNull
    @Size(min = 4)
    private String zipCode;
    @NotNull
    @Size(min = 5)
    private String city;
    @NotNull
    @Size(min = 3)
    private String state;
    @NotNull
    @Size(min = 3)
    private String country;

    public Address() {
    }


    public Address(String streetAddress, String zipCode, String city, String state, String country) {
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }
}