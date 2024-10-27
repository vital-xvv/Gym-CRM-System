package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.dto.trainee.CreateTraineeDto;
import com.epam.vital.gym_crm.dto.trainee.UpdateTraineeDto;
import com.epam.vital.gym_crm.dto.trainer.CreateTrainerDto;
import com.epam.vital.gym_crm.dto.trainer.UpdateTrainerDto;
import com.epam.vital.gym_crm.dto.training.CreateTrainingDto;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.service.TraineeService;
import com.epam.vital.gym_crm.service.TrainerService;
import com.epam.vital.gym_crm.service.TrainingService;
import com.epam.vital.gym_crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(FacadeController.BASE_URL)
public class FacadeController {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;

    public static final String BASE_URL = "/api";
    public static final String TRAINING_ENDPOINT = "/training";
    public static final String TRAINER_ENDPOINT = "/trainer";
    public static final String TRAINEE_ENDPOINT = "/trainee";

    @Autowired
    public FacadeController(TrainerService trainerService, TrainingService trainingService, TraineeService traineeService, UserService userService) {
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.userService = userService;
    }

    //Training Endpoints
    @PostMapping(TRAINING_ENDPOINT + "/create")
    public ResponseEntity<?> createTraining(@NotBlank @RequestParam("username") String username,
                                            @NotBlank @RequestParam("password") String password,
                                            @Valid @RequestBody CreateTrainingDto trainingDto) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        trainingService.createTraining(trainingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("created", true));
    }

    @GetMapping(TRAINING_ENDPOINT + "/{id}")
    public ResponseEntity<Training> getTrainingById(@NotBlank @RequestParam("username") String username,
                                                    @NotBlank @RequestParam("password") String password,
                                                    @PathVariable Long id) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Training training = trainingService.getTrainingById(id);
        return training != null ? ResponseEntity.ok(training) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(TRAINING_ENDPOINT + "/list")
    public ResponseEntity<List<Training>> getAllTrainings(@NotBlank @RequestParam("username") String username,
                                                          @NotBlank @RequestParam("password") String password) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        List<Training> trainings = trainingService.getListOfTrainings();
        return ResponseEntity.ok(trainings);
    }
    //END Training Endpoints

    //Trainer Endpoints
    @GetMapping(TRAINER_ENDPOINT + "/{trainerUsername}")
    public ResponseEntity<Trainer> getTrainerById(@NotBlank @RequestParam("username") String username,
                                                  @NotBlank @RequestParam("password") String password,
                                                  @PathVariable String trainerUsername) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Optional<Trainer> trainer = trainerService.findTrainerProfileByUsername(trainerUsername);
        return trainer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(TRAINER_ENDPOINT + "/list")
    public ResponseEntity<List<Trainer>> getAllTrainers(@NotBlank @RequestParam("username") String username,
                                                        @NotBlank @RequestParam("password") String password) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        List<Trainer> trainers = trainerService.getTrainerProfiles();
        return ResponseEntity.ok(trainers);
    }

    @PostMapping(TRAINER_ENDPOINT + "/create")
    public ResponseEntity<?> createTrainer(@Valid @RequestBody CreateTrainerDto trainer) {
        trainerService.createTrainerProfile(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("created", true));

    }

    @PutMapping(TRAINER_ENDPOINT + "/update")
    public ResponseEntity<?> updateTrainer(@NotBlank @RequestParam("username") String username,
                                           @NotBlank @RequestParam("password") String password,
                                           @Valid @RequestBody UpdateTrainerDto trainerDto) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        trainerService.updateTrainerProfile(trainerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //END Trainer Endpoints

    //Trainee Endpoints
    @GetMapping(TRAINEE_ENDPOINT + "/list")
    public ResponseEntity<List<Trainee>> getListOfTrainees(@NotBlank @RequestParam("username") String username,
                                                           @NotBlank @RequestParam("password") String password){
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(traineeService.getTraineeProfiles());
    }

    @GetMapping(TRAINEE_ENDPOINT + "/{id}")
    public ResponseEntity<?> getTraineeById(@NotBlank @RequestParam("username") String username,
                                            @NotBlank @RequestParam("password") String password,
                                            @PathVariable Long id) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Optional<Trainee> trainee = traineeService.getTraineeById(id);
        return ResponseEntity.status(trainee.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(trainee);
    }

    @DeleteMapping(TRAINEE_ENDPOINT + "/{id}")
    public ResponseEntity<?> deleteTraineeById(@NotBlank @RequestParam("username") String username,
                                               @NotBlank @RequestParam("password") String password,
                                               @PathVariable Long id) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        boolean deleted = traineeService.deleteTraineeById(id);
        return ResponseEntity.status(deleted ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(TRAINEE_ENDPOINT + "/create")
    public ResponseEntity<?> createTraineeById(@Valid @RequestBody CreateTraineeDto trainee) {
        traineeService.createTraineeProfile(trainee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(TRAINEE_ENDPOINT + "/update")
    public ResponseEntity<?> updateTraineeById(@NotBlank @RequestParam("username") String username,
                                               @NotBlank @RequestParam("password") String password,
                                               @Valid @RequestBody UpdateTraineeDto traineeDto) {
        if (!userService.authenticateUser(username, password)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        traineeService.updateTraineeProfile(traineeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(traineeService.findTraineeProfileByUsername(traineeDto.getUsername()));
    }
    //END Trainee Endpoints
}
