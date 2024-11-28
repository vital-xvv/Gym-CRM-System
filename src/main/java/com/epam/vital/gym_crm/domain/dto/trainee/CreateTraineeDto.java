package com.epam.vital.gym_crm.domain.dto.trainee;

import com.epam.vital.gym_crm.domain.dto.user.CreateUserDto;
import com.epam.vital.gym_crm.domain.model.Trainee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateTraineeDto (
    @NotNull(message = "User can not be null.")
    @Valid
    CreateUserDto user,
    @NotNull(message = "Birth date can not be null.")
    LocalDate birthDate
) {
    public Trainee toTrainee() {
        return Trainee.builder()
                .user(user.toUser())
                .birthDate(birthDate)
                .build();
    }
}
