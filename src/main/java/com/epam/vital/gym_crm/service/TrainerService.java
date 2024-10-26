package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.criteria.TrainerCriteriaBuilder;
import com.epam.vital.gym_crm.dict.TrainingType;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TrainerService {
    private final TrainerRepository repository;
    private final UserService userService;
    private final TrainerCriteriaBuilder criteriaBuilder;

    @Autowired
    public TrainerService(TrainerRepository repository, UserService userService, TrainerCriteriaBuilder criteriaBuilder) {
        this.repository = repository;
        this.userService = userService;
        this.criteriaBuilder = criteriaBuilder;
    }

    public List<Trainer> getTrainerProfiles() {
        return repository.findAll();
    }

    public void createTrainerProfile(Trainer trainer) {
        userService.setUserUsername(trainer.getUser());
        repository.save(trainer);
    }

    public Optional<Trainer> findTrainerProfileByUsername(String username) {
        return repository.findByUser_Username(username);
    }

    public boolean changeTrainerProfilePassword(String username, String oldPassword, String newPassword) {
        return userService.changeUserProfilePassword(username, oldPassword, newPassword);
    }

    public void updateTrainerProfile(Trainer trainer) {
        repository.save(trainer);
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
}
