package com.epam.vital.gym_crm.dto.trainee;

import com.epam.vital.gym_crm.dto.user.CreateUserDto;
import com.epam.vital.gym_crm.model.Trainee;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateTraineeDto {
    @NotNull
    private CreateUserDto user;
    @NotNull
    private LocalDate birthDate;

    public Trainee toTrainee() {
        return Trainee.builder()
                .user(user.toUser())
                .birthDate(birthDate)
                .build();
    }
}
