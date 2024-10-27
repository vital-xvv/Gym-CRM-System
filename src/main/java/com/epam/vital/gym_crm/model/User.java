package com.epam.vital.gym_crm.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean isActive;
    @OneToOne(optional = false)
    private Address address;
}
