package com.epam.vital.gym_crm.dto.training;

import com.epam.vital.gym_crm.dict.TrainingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateTrainingDto {
    @NotBlank
    private String trainingName;
    @NotNull
    @Size(min = 1)
    private List<String> traineeUsernames;
    @NotBlank
    private String trainerUsername;
    @NotNull
    @Size(min = 1)
    private List<TrainingType> trainingTypes;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    private Duration duration;
}
