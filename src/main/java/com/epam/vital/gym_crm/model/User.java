package com.epam.vital.gym_crm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
}
