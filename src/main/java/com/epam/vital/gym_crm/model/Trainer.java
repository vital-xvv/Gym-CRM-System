package com.epam.vital.gym_crm.model;

import com.epam.vital.gym_crm.dict.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Trainer {
    private Long id;
    private List<Specialization> trainerSpecializations;
    private Long userId;
}
