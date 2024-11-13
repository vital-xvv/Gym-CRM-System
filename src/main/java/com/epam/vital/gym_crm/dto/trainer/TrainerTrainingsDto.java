package com.epam.vital.gym_crm.dto.trainer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrainerTrainingsDto {
    @NotBlank
    private String trainerUsername;
    private LocalDateTime from;
    private LocalDateTime to;
    private String traineeName;
}
