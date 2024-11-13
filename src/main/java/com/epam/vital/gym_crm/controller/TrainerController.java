package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.dto.trainer.CreateTrainerDto;
import com.epam.vital.gym_crm.dto.trainer.TrainerTrainingsDto;
import com.epam.vital.gym_crm.dto.trainer.UpdateTrainerDto;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.User;
import com.epam.vital.gym_crm.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    public final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTrainer(@Valid @RequestBody CreateTrainerDto dto) {
        User trainer = trainerService.createTrainerProfile(dto).getUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "username", trainer.getUsername(),
                        "password", trainer.getPassword()
                ));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getTrainerProfileByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(trainerService.findTrainerProfileByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTrainerProfile(@Valid @RequestBody UpdateTrainerDto dto) {
        return ResponseEntity.ok(trainerService.updateTrainerProfile(dto));
    }

    @GetMapping("/not/assigned/trainee/{traineeUsername}")
    public ResponseEntity<?> getNotAssignedTrainersOnTrainee(@PathVariable("traineeUsername") String traineeUsername) {
        return ResponseEntity.ok(trainerService.getTrainersWithoutTraineeByUsername(traineeUsername));
    }

    @PostMapping("/trainings")
    public ResponseEntity<?> getTrainerTrainings(@Valid @RequestBody TrainerTrainingsDto dto) {
        return ResponseEntity.ok(trainerService.getTrainerTrainingsByCriteria(dto));
    }
}
