package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository repository;

    public boolean createTraining(Training training) {
        log.info("Creating a training...");
        return repository.create(training);
    }

    public Training getTrainingById(Long id) {
        log.info("Finding a training with id {}...", id);
        Optional<Training> training = repository.getById(id);
        if (training.isPresent()) return training.get();
        else {
            log.error("Training with a specified id {} was not found.", id);
            return null;
        }
    }

    public List<Training> getListOfTrainings() {
        log.info("Retrieving all trainings from DB...");
        return repository.getAllTrainings();
    }
}
