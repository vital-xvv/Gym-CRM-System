package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.service.TraineeService;
import com.epam.vital.gym_crm.service.TrainerService;
import com.epam.vital.gym_crm.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(FacadeController.BASE_URL)
public class FacadeController {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public static final String BASE_URL = "/api";
    public static final String TRAINING_ENDPOINT = "/training";
    public static final String TRAINER_ENDPOINT = "/trainer";
    public static final String TRAINEE_ENDPOINT = "/trainee";

    @Autowired
    public FacadeController(TrainerService trainerService, TrainingService trainingService, TraineeService traineeService) {
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.traineeService = traineeService;
    }

    //Training Endpoints
    @PostMapping(TRAINING_ENDPOINT + "/create")
    public ResponseEntity<?> createTraining(@RequestBody Training training) {
        trainingService.createTraining(training);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("created", true));
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
    //END Training Endpoints

    //Trainer Endpoints
    @GetMapping(TRAINER_ENDPOINT + "/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        Trainer trainer = trainerService.findTrainerById(id);
        return trainer != null ? ResponseEntity.ok(trainer) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(TRAINER_ENDPOINT + "/list")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getListOfTrainers();
        return ResponseEntity.ok(trainers);
    }

    @PostMapping(TRAINER_ENDPOINT + "/create")
    public ResponseEntity<?> createTrainer(@RequestBody Trainer trainer) {
        trainerService.createTrainer(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("created", true));

    }

    @PutMapping(TRAINER_ENDPOINT + "/update")
    public ResponseEntity<?> updateTrainer(@RequestBody Trainer trainer) {
        trainerService.updateTrainer(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //END Trainer Endpoints

    //Trainee Endpoints
    @GetMapping(TRAINEE_ENDPOINT + "/list")
    public ResponseEntity<List<Trainee>> getListOfTrainees(){
        return ResponseEntity.ok(traineeService.getListOfTrainees());
    }

    @GetMapping(TRAINEE_ENDPOINT + "/{id}")
    public ResponseEntity<?> getTraineeById(@PathVariable Long id) {
        Optional<Trainee> trainee = traineeService.getTraineeById(id);
        return ResponseEntity.status(trainee.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(trainee);
    }

    @DeleteMapping(TRAINEE_ENDPOINT + "/{id}")
    public ResponseEntity<?> deleteTraineeById(@PathVariable Long id) {
        boolean deleted = traineeService.deleteTraineeById(id);
        return ResponseEntity.status(deleted ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(TRAINEE_ENDPOINT + "/create")
    public ResponseEntity<?> createTraineeById(@RequestBody Trainee trainee) {
        traineeService.createTrainee(trainee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(TRAINEE_ENDPOINT + "/update")
    public ResponseEntity<?> updateTraineeById(@PathVariable Long id) {
        boolean updated = traineeService.deleteTraineeById(id);
        return ResponseEntity.status(updated ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(traineeService.getTraineeById(id));
    }
    //END Trainee Endpoints
}
