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
    @NotBlank(message = "A training name can not be null or empty.")
    private String trainingName;
    @NotNull(message = "Trainee usernames list can not be null.")
    @Size(min = 1, message = "Trainee usernames list can not be empty.")
    private List<String> traineeUsernames;
    @NotBlank(message = "Trainer username can not be empty.")
    private String trainerUsername;
    @NotNull(message = "Training types list can not be null.")
    @Size(min = 1, message = "Training types list can not be empty.")
    private List<TrainingType> trainingTypes;
    @NotNull(message = "Training date can not be null.")
    private LocalDateTime dateTime;
    @NotNull(message = "Training duration can not be null.")
    private Duration duration;
}
