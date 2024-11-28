package com.epam.vital.gym_crm.domain.dto.trainer;

import com.epam.vital.gym_crm.domain.dict.Specialization;
import com.epam.vital.gym_crm.domain.dto.user.UpdateUserDto;
import com.epam.vital.gym_crm.domain.model.Trainer;
import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.Training;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

public record UpdateTrainerDto (
    @NotNull(message = "User can not be null.")
    @Valid
    UpdateUserDto user,
    List<Long> traineeIds,
    List<Long> trainingIds,
    @NotNull(message = "Trainer specializations list can not be null.")
    @Size(min = 1, message = "Trainer specializations list can not be empty.")
    List<Specialization> trainerSpecializations
) {
    public Trainer toTrainer() {
        return Trainer.builder()
                .user(user.toUser())
                .trainerSpecializations(trainerSpecializations)
                .trainings(trainingIds.stream().map(i -> Training.builder().id(i).build()).collect(Collectors.toList()))
                .trainees(traineeIds.stream().map(i -> Trainee.builder().id(i).build()).collect(Collectors.toList()))
                .build();
    }
}
