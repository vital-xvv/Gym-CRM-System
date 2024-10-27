package com.epam.vital.gym_crm.dto.trainee;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateTraineeDto {
    @NotBlank
    private String username;
    @NotNull
    private List<String> trainerUsernames;
    @NotNull
    private List<Long> trainingIds;
    @NotNull
    private LocalDate birthDate;
}
