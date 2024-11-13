package com.epam.vital.gym_crm.dto.trainer;

import com.epam.vital.gym_crm.dict.Specialization;
import com.epam.vital.gym_crm.dto.user.CreateUserDto;
import com.epam.vital.gym_crm.model.Trainer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateTrainerDto {
    @NotNull(message = "User can not be null.")
    @Valid
    private CreateUserDto user;
    @NotNull(message = "Trainer specializations list can not be null.")
    @Size(min = 1, message = "Trainer specialization list can not be empty, at least one specialization must be present.")
    private List<Specialization> trainerSpecializations;

    public Trainer toTrainer() {
        return Trainer.builder()
                .user(user.toUser())
                .trainerSpecializations(trainerSpecializations)
                .build();
    }
}
