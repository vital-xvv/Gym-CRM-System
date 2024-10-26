package com.epam.vital.gym_crm.model;

import com.epam.vital.gym_crm.dict.TrainingType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Data
@Entity
public class Training {
    @Id
    private Long id;
    @Column(nullable = false)
    private String trainingName;
    @OneToMany(cascade = CascadeType.DETACH)
    private List<Trainee> traineeList;
    @OneToMany(cascade = CascadeType.DETACH)
    private List<Trainer> trainerList;
    @Enumerated(EnumType.STRING)
    private List<TrainingType> trainingTypes;
    private LocalDateTime dateTime;
    private Duration duration;
}
