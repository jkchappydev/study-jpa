package org.example.jpql;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {

    private String city;

    private String street;

    private String zipCode;

}
