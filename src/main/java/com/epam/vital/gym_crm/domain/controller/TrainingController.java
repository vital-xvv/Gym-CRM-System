package com.epam.vital.gym_crm.domain.controller;

import com.epam.vital.gym_crm.domain.dict.TrainingType;
import com.epam.vital.gym_crm.domain.dto.training.CreateTrainingDto;
import com.epam.vital.gym_crm.http.util.HttpUrlsDict;
import com.epam.vital.gym_crm.domain.service.TrainingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(HttpUrlsDict.TRAINING_URL + HttpUrlsDict.CURRENT_VERSION)
public class TrainingController {
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTraining(@Valid @RequestBody CreateTrainingDto dto) {
        trainingService.createTraining(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types")
    public ResponseEntity<?> getAllTrainingTypes() {
        return ResponseEntity.ok(TrainingType.values());
    }
}
