package com.epam.vital.gym_crm.domain.dto.trainer;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TrainerTrainingsDto (
    @NotBlank(message = "Trainer's username can not be null or empty.")
    String trainerUsername,
    LocalDateTime from,
    LocalDateTime to,
    String traineeName
) {}
