package com.epam.vital.gym_crm.model;

import com.epam.vital.gym_crm.dict.Specialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {
    @Id
    private Long id;
    @OneToOne
    private User user;
    @OneToMany
    private List<Trainee> traineeList;
    @Enumerated(EnumType.STRING)
    private List<Specialization> trainerSpecializations;
}
