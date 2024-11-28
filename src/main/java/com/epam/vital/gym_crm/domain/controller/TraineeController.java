package com.epam.vital.gym_crm.domain.controller;

import com.epam.vital.gym_crm.domain.dto.trainee.CreateTraineeDto;
import com.epam.vital.gym_crm.domain.dto.trainee.UpdateTraineeDto;
import com.epam.vital.gym_crm.domain.dto.trainee.TraineeTrainingsDto;
import com.epam.vital.gym_crm.http.util.HttpUrlsDict;
import com.epam.vital.gym_crm.domain.model.User;
import com.epam.vital.gym_crm.domain.service.TraineeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(HttpUrlsDict.TRAINEE_URL + HttpUrlsDict.CURRENT_VERSION)
public class TraineeController {
    private final TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTrainee(@Valid @RequestBody CreateTraineeDto dto) {
        User trainee = traineeService.createTraineeProfile(dto).getUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "username", trainee.getUsername(),
                        "password", trainee.getPassword()
                ));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getTraineeProfileByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(traineeService.findTraineeProfileByUsername(username));
    }

    @PutMapping("/{traineeUsername}/add/trainers")
    public ResponseEntity<?> assignTrainerToTrainee(@PathVariable("traineeUsername") String traineeUsername,
                                                    @RequestBody List<String> trainerUsernames) {
        return ResponseEntity.status(HttpStatus.CREATED).body(traineeService.assignTrainerProfilesToTraineeProfileByUsernames(traineeUsername, trainerUsernames));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteTraineeByUsername(@PathVariable("username") String username) {
        traineeService.deleteTraineeProfileByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTraineeProfile(@Valid @RequestBody UpdateTraineeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(traineeService.updateTraineeProfile(dto));
    }

    @PostMapping("/trainings")
    public ResponseEntity<?> getTraineeTrainings(@Valid @RequestBody TraineeTrainingsDto dto) {
        return ResponseEntity.ok(traineeService.findTraineeTrainingsByCriteria(dto));
    }
}
