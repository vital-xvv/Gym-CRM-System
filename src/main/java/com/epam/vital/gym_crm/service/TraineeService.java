package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.criteria.TraineeCriteriaBuilder;
import com.epam.vital.gym_crm.dict.TrainingType;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.repository.TraineeRepository;
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
public class TraineeService {
    private final TraineeRepository repository;
    private final UserService userService;
    private final TraineeCriteriaBuilder criteriaBuilder;

    @Autowired
    public TraineeService(TraineeRepository repository, UserService userService, TraineeCriteriaBuilder criteriaBuilder) {
        this.repository = repository;
        this.userService = userService;
        this.criteriaBuilder = criteriaBuilder;
    }

    public Optional<Trainee> getTraineeById(Long id) {
        Optional<Trainee> trainee = repository.findById(id);
        if (trainee.isEmpty()) log.error("Trainee with id {} does not exist.", id);
        return trainee;
    }

    public List<Trainee> getTraineeProfiles() {
        return repository.findAll();
    }

    public boolean deleteTraineeById(Long id) {
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    public void createTraineeProfile(Trainee trainee) {
        userService.setUserUsername(trainee.getUser());
        repository.save(trainee);
    }

    public void updateTraineeProfile(Trainee trainee) {
        repository.save(trainee);
    }

    public Optional<Trainee> findTraineeProfileByUsername(String username) {
        return repository.findByUser_Username(username);
    }

    public boolean changeTraineeProfilePassword(String username, String oldPassword, String newPassword) {
        return userService.changeUserProfilePassword(username, oldPassword, newPassword);
    }

    public void toggleTraineeProfileActivation(String username, boolean isActive) {
        userService.changeUserProfileActivation(username, isActive);
    }

    public void deleteTraineeProfileByUsername(String username) {
        userService.deleteUserProfileByUsername(username);
    }

    public List<Training> findTraineeTrainingsByCriteria(
            String traineeUsername,
            LocalDateTime from,
            LocalDateTime to,
            String trainerName,
            TrainingType trainingType
    ) {
        return criteriaBuilder.findAllTraineeTrainingsByCriteria(traineeUsername, from, to, trainerName, trainingType);
    }
}
