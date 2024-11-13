package com.epam.vital.gym_crm.model;

import com.epam.vital.gym_crm.dict.TrainingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String trainingName;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "training_trainee",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id"))
    private List<Trainee> trainees;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @ElementCollection(targetClass = TrainingType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "training_types", joinColumns = @JoinColumn(name = "training_id"))
    @Enumerated(EnumType.STRING)
    private List<TrainingType> trainingTypes;
    private LocalDateTime dateTime;
    private Duration duration;
}
