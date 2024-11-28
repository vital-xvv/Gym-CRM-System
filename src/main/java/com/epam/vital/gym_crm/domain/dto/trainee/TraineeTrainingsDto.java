package com.epam.vital.gym_crm.domain.dto.trainee;

import com.epam.vital.gym_crm.domain.dict.TrainingType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TraineeTrainingsDto (
    @NotBlank(message = "A trainee's username can not be blank or empty.")
    String traineeUsername,
    LocalDateTime from,
    LocalDateTime to,
    String trainerName,
    TrainingType trainingType
) {}
