package com.epam.vital.gym_crm.model;

import com.epam.vital.gym_crm.dict.TrainingType;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Data
public class Training {
    private Long id;
    private Long trainerId;
    private List<Long> traineeIdList;

    private TrainingType trainingType;
    private LocalDateTime dateTime;
    private Duration duration;
}
