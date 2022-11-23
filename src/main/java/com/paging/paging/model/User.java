package com.paging.paging.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@SuppressWarnings("JpaObjectClassSignatureInspection")
@Entity
@Table(name = "users")
@ToString
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    private User(){
    }

    public User(String firstName, String lastName,String phoneNumber, String emailAddress, Address address) {
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
}
