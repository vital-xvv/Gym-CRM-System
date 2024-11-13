package com.epam.vital.gym_crm.dto.trainee;

import com.epam.vital.gym_crm.dto.user.CreateUserDto;
import com.epam.vital.gym_crm.model.Trainee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTraineeDto {
    @NotNull(message = "User can not be null.")
    @Valid
    private CreateUserDto user;
    @NotNull(message = "Birth date can not be null.")
    private LocalDate birthDate;

    public Trainee toTrainee() {
        return Trainee.builder()
                .user(user.toUser())
                .birthDate(birthDate)
                .build();
    }
}
