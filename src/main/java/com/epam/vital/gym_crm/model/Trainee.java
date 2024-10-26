package com.epam.vital.gym_crm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Trainee {
    @Id
    private Long id;
    @OneToOne
    private User user;
    @OneToMany
    private List<Trainer> trainerList;
    private LocalDate birthDate;
}
