package com.epam.vital.gym_crm.dto.trainee;

import com.epam.vital.gym_crm.dto.user.UpdateUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateTraineeDto {
    @NotNull
    private UpdateUserDto user;
    @NotNull
    private List<String> trainerUsernames;
    @NotNull
    private List<Long> trainingIds;
    @NotNull
    private LocalDate birthDate;
}
