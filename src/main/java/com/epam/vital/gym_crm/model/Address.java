package com.epam.vital.gym_crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private Long id;
    private String street;
    private String city;
    private String countryCode;
    private String postalCode;
}
