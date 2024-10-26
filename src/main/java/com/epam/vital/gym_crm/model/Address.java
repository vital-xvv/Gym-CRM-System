package com.epam.vital.gym_crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Address {
    @Id
    private Long id;
    private String street;
    private String city;
    private String countryCode;
    private String postalCode;
}
