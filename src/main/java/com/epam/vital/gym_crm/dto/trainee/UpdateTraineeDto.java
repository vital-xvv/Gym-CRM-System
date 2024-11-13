package com.epam.vital.gym_crm.dto.trainee;

import com.epam.vital.gym_crm.dto.user.UpdateUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateTraineeDto {
    @NotNull(message = "User can not be null.")
    @Valid
    private UpdateUserDto user;
    @NotNull(message = "Trainer usernames list can not be null.")
    private List<String> trainerUsernames;
    @NotNull(message = "Training ids list can not be null.")
    private List<Long> trainingIds;
    @NotNull(message = "Birth date can not be null.")
    private LocalDate birthDate;
}
