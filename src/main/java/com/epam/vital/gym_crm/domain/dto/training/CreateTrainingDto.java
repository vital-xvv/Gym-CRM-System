package com.epam.vital.gym_crm.domain.dto.training;

import com.epam.vital.gym_crm.domain.dict.TrainingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public record CreateTrainingDto (
    @NotBlank(message = "A training name can not be null or empty.")
    String trainingName,
    @NotNull(message = "Trainee usernames list can not be null.")
    @Size(min = 1, message = "Trainee usernames list can not be empty.")
    List<String> traineeUsernames,
    @NotBlank(message = "Trainer username can not be empty.")
    String trainerUsername,
    @NotNull(message = "Training types list can not be null.")
    @Size(min = 1, message = "Training types list can not be empty.")
    List<TrainingType> trainingTypes,
    @NotNull(message = "Training date can not be null.")
    LocalDateTime dateTime,
    @NotNull(message = "Training duration can not be null.")
    Duration duration
) {}
