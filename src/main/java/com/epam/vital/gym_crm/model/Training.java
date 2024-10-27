package com.epam.vital.gym_crm.model;

import com.epam.vital.gym_crm.dict.TrainingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Data
@Entity
@Table(name = "training")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    private Long id;
    @Column(nullable = false)
    private String trainingName;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "training_trainee",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id"))
    private List<Trainee> trainees;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @Enumerated(EnumType.STRING)
    private List<TrainingType> trainingTypes;
    private LocalDateTime dateTime;
    private Duration duration;
}
