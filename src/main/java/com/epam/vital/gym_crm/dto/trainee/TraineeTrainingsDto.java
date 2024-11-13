package com.epam.vital.gym_crm.dto.trainee;

import com.epam.vital.gym_crm.dict.TrainingType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TraineeTrainingsDto {
    @NotBlank
    private String traineeUsername;
    private LocalDateTime from;
    private LocalDateTime to;
    private String trainerName;
    private TrainingType trainingType;
}
