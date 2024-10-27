package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.dto.training.CreateTrainingDto;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.repository.TraineeRepository;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import com.epam.vital.gym_crm.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
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

    public void createTraining(CreateTrainingDto trainingDto) {
        log.info("Creating a training...");
        repository.save(convertCreateTrainingDtoToTraining(trainingDto));
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

    private Training convertCreateTrainingDtoToTraining(CreateTrainingDto trainingDto) {
        return Training.builder()
                .trainer(trainerRepository.findByUser_Username(trainingDto.getTrainerUsername()).orElse(null))
                .trainingName(trainingDto.getTrainingName())
                .trainingTypes(trainingDto.getTrainingTypes())
                .duration(trainingDto.getDuration())
                .dateTime(trainingDto.getDateTime())
                .trainees(trainingDto.getTraineeUsernames().stream().map(traineeRepository::findByUser_Username).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .build();
    }
}
