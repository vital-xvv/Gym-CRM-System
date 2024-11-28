package com.epam.vital.gym_crm.domain.service;

import com.epam.vital.gym_crm.domain.dto.training.CreateTrainingDto;
import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.Trainer;
import com.epam.vital.gym_crm.domain.model.Training;
import com.epam.vital.gym_crm.domain.repository.TraineeRepository;
import com.epam.vital.gym_crm.domain.repository.TrainerRepository;
import com.epam.vital.gym_crm.domain.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainingService {
    private final TrainingRepository repository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;

    @Autowired
    public TrainingService(TrainingRepository repository, TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        this.repository = repository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
    }

    @Transactional
    public void createTraining(CreateTrainingDto trainingDto) {
        log.info("Creating a training...");

        Optional<Trainer> trainer = trainerRepository.findByUser_Username(trainingDto.trainerUsername());
        List<Trainee> trainees = trainingDto.traineeUsernames().stream().map(traineeRepository::findByUser_Username).filter(Optional::isPresent).map(Optional::get).toList();

        if (trainer.isPresent() && !trainees.isEmpty()) {
            Training training = new Training();
            training.setTrainer(trainer.get());
            training.setTrainees(trainees);
            training.setTrainingName(trainingDto.trainingName());
            training.setTrainingTypes(trainingDto.trainingTypes());
            training.setDuration(trainingDto.duration());
            training.setDateTime(trainingDto.dateTime());
            repository.save(training);
        } else {
            log.error("Data provided to create a Training is incorrect!");
        }
    }

    public Training getTrainingById(Long id) {
        log.info("Finding a training with id {}...", id);
        Optional<Training> training = repository.findById(id);
        if (training.isPresent()) return training.get();
        else {
            log.error("Training with a specified id {} was not found.", id);
            return null;
        }
    }

    public List<Training> getListOfTrainings() {
        log.info("Retrieving all trainings from DB...");
        return repository.findAll();
    }
}
