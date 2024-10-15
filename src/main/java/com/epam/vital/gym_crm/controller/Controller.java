package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.service.TraineeService;
import com.epam.vital.gym_crm.service.TrainerService;
import com.epam.vital.gym_crm.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(Controller.BASE_URL)
public class Controller {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public static final String BASE_URL = "gym_crm/api";
    public static final String TRAINING_ENDPOINT = "/training";

    //Training Endpoints
    @PostMapping(TRAINING_ENDPOINT + "/create")
    public ResponseEntity<?> createTraining(@RequestBody Training training) {
        boolean created = trainingService.createTraining(training);
        return created ? ResponseEntity.status(HttpStatus.CREATED).body(Map.of("created", true)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(TRAINING_ENDPOINT + "/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id) {
        Training training = trainingService.getTrainingById(id);
        return training != null ? ResponseEntity.ok(training) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(TRAINING_ENDPOINT + "/list")
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> trainings = trainingService.getListOfTrainings();
        return ResponseEntity.ok(trainings);
    }


}
