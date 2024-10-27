package com.epam.vital.gym_crm.dto.trainer;

import com.epam.vital.gym_crm.dict.Specialization;
import com.epam.vital.gym_crm.dto.user.CreateUserDto;
import com.epam.vital.gym_crm.model.Trainer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateTrainerDto {
    @NotNull
    private CreateUserDto user;
    @NotNull
    @Size(min = 1)
    private List<Specialization> trainerSpecializations;

    public Trainer toTrainer() {
        return Trainer.builder()
                .user(user.toUser())
                .trainerSpecializations(trainerSpecializations)
                .build();
    }
}
