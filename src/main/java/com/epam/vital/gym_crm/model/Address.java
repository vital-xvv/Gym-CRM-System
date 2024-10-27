package com.epam.vital.gym_crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    private Long id;
    private String street;
    private String city;
    private String countryCode;
    private String postalCode;
}
