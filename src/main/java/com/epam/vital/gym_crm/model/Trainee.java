package com.epam.vital.gym_crm.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Trainee {
    private Long id;
    private LocalDate birthDate;
    private Address address;
    private Long userId;
}
