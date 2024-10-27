package com.epam.vital.gym_crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "trainee")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trainee {
    @Id
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany(mappedBy = "trainees")
    private List<Trainer> trainers;
    @ManyToMany(mappedBy = "trainees")
    private List<Training> trainings;
    private LocalDate birthDate;
}
