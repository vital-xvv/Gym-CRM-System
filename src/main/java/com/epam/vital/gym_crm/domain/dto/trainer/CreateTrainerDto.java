package com.epam.vital.gym_crm.domain.dto.trainer;

import com.epam.vital.gym_crm.domain.dict.Specialization;
import com.epam.vital.gym_crm.domain.dto.user.CreateUserDto;
import com.epam.vital.gym_crm.domain.model.Trainer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateTrainerDto (
    @NotNull(message = "User can not be null.")
    @Valid
    CreateUserDto user,
    @NotNull(message = "Trainer specializations list can not be null.")
    @Size(min = 1, message = "Trainer specialization list can not be empty, at least one specialization must be present.")
    List<Specialization> trainerSpecializations
) {
    public Trainer toTrainer() {
        return Trainer.builder()
                .user(user.toUser())
                .trainerSpecializations(trainerSpecializations)
                .build();
    }
}
