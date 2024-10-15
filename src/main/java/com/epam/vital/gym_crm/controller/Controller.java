package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.repository.TraineeRepository;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import com.epam.vital.gym_crm.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;


}
