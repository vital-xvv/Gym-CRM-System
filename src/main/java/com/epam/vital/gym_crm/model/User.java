package com.epam.vital.gym_crm.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class User {
    public Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
}
