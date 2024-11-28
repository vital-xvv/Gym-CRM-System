package com.epam.vital.gym_crm.domain.dto.training;

import com.epam.vital.gym_crm.domain.dict.TrainingType;
import com.epam.vital.gym_crm.domain.dto.trainer.TrainerProfile;
import com.epam.vital.gym_crm.domain.model.Training;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrainingProfile {
    private String trainingName;
    private TrainerProfile trainer;
    private List<TrainingType> trainingTypes;
    private LocalDateTime dateTime;
    private Duration duration;

    public TrainingProfile of(Training training) {
        return TrainingProfile.builder()
                .trainingName(training.getTrainingName())
                .trainer(TrainerProfile.of(training.getTrainer()))
                .trainingTypes(training.getTrainingTypes())
                .dateTime(training.getDateTime())
                .duration(training.getDuration())
                .build();
    }
}
