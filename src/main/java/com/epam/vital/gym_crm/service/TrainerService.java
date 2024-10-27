package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.criteria.TrainerCriteriaBuilder;
import com.epam.vital.gym_crm.dict.TrainingType;
import com.epam.vital.gym_crm.dto.trainer.CreateTrainerDto;
import com.epam.vital.gym_crm.dto.trainer.UpdateTrainerDto;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.repository.TraineeRepository;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import com.epam.vital.gym_crm.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TrainerService {
    private final TrainerRepository repository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;
    private final UserService userService;
    private final TrainerCriteriaBuilder criteriaBuilder;

    @Autowired
    public TrainerService(TrainerRepository repository, TraineeRepository traineeRepository, TrainingRepository trainingRepository, UserService userService, TrainerCriteriaBuilder criteriaBuilder) {
        this.repository = repository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.userService = userService;
        this.criteriaBuilder = criteriaBuilder;
    }

    public List<Trainer> getTrainerProfiles() {
        return repository.findAll();
    }

    public void createTrainerProfile(CreateTrainerDto trainerDto) {
        Trainer trainer = trainerDto.toTrainer();
        userService.setUserUsername(trainer.getUser());
        repository.save(trainer);
    }

    public Optional<Trainer> findTrainerProfileByUsername(String username) {
        return repository.findByUser_Username(username);
    }

    public boolean changeTrainerProfilePassword(String username, String oldPassword, String newPassword) {
        return userService.changeUserProfilePassword(username, oldPassword, newPassword);
    }

    public void updateTrainerProfile(UpdateTrainerDto trainerDto) {
        repository.save(convertUpdateTrainerToTrainer(trainerDto));
    }

    public void toggleTrainerProfileActivation(String username, boolean isActive) {
        userService.changeUserProfileActivation(username, isActive);
    }

    public List<Training> getTrainerTrainingsByCriteria(String trainerUsername, LocalDateTime from, LocalDateTime to, String traineeName, TrainingType trainingType) {
        return criteriaBuilder.findAllTrainerTrainingsByCriteria(trainerUsername, from, to, traineeName, trainingType);
    }

    public List<Trainer> getTrainersWithoutTraineeByUsername(String traineeUsername) {
        return criteriaBuilder.findTrainersWithNoMutualTrainingsWithTrainee(traineeUsername);
    }

    private Trainer convertUpdateTrainerToTrainer(UpdateTrainerDto trainerDto) {
        return Trainer.builder()
                .user(userService.findUserByUsername(trainerDto.getUsername()).orElse(null))
                .trainees(trainerDto.getTraineeUsernames().stream().map(traineeRepository::findByUser_Username).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .trainings(trainerDto.getTrainingIds().stream().map(trainingRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .id(repository.findByUser_Username(trainerDto.getUsername()).map(Trainer::getId).orElse(null))
                .trainerSpecializations(trainerDto.getTrainerSpecializations())
                .build();
    }
}
