package com.epam.vital.gym_crm.model;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private String street;
    private String city;
    private String countryCode;
    private String postalCode;
}
