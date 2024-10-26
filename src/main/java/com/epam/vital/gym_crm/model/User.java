package com.epam.vital.gym_crm.model;

import com.epam.vital.gym_crm.util.UserUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Entity
public class User {
    @Id
    public Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean isActive;
    @OneToOne
    private Address address;
}
