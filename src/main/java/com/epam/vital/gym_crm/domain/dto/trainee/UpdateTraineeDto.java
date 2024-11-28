package com.epam.vital.gym_crm.domain.dto.trainee;

import com.epam.vital.gym_crm.domain.dto.user.UpdateUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UpdateTraineeDto (
    @NotNull(message = "User can not be null.")
    @Valid
    UpdateUserDto user,
    @NotNull(message = "Trainer usernames list can not be null.")
    List<String> trainerUsernames,
    @NotNull(message = "Training ids list can not be null.")
    List<Long> trainingIds,
    @NotNull(message = "Birth date can not be null.")
    LocalDate birthDate
) {}
