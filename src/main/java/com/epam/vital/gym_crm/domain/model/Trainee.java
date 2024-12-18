package com.epam.vital.gym_crm.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private User user;
    @ManyToMany(mappedBy = "trainees", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Trainer> trainers;
    @JsonIgnore
    @ManyToMany(mappedBy = "trainees", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Training> trainings;
    private LocalDate birthDate;
}
