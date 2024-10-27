package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.criteria.TraineeCriteriaBuilder;
import com.epam.vital.gym_crm.dict.TrainingType;
import com.epam.vital.gym_crm.dto.trainee.CreateTraineeDto;
import com.epam.vital.gym_crm.dto.trainee.UpdateTraineeDto;
import com.epam.vital.gym_crm.model.Trainee;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TraineeService {
    private final TraineeRepository repository;
    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final UserService userService;
    private final TraineeCriteriaBuilder criteriaBuilder;

    @Autowired
    public TraineeService(TraineeRepository repository, UserService userService, TraineeCriteriaBuilder criteriaBuilder, TrainingRepository trainingRepository, TrainerRepository trainerRepository) {
        this.repository = repository;
        this.userService = userService;
        this.criteriaBuilder = criteriaBuilder;
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
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
        Optional<Trainee> trainee = repository.findById(id);
        List<Training> trainings = trainee.map(trainingRepository::findAllByTraineesContains).orElse(Collections.emptyList());
        trainings.stream().filter(t -> t.getTrainees().size() == 1).forEach(trainingRepository::delete);
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    public void createTraineeProfile(CreateTraineeDto traineeDto) {
        Trainee trainee = traineeDto.toTrainee();
        userService.setUserUsername(trainee.getUser());
        repository.save(trainee);
    }

    public void updateTraineeProfile(UpdateTraineeDto traineeDto) {
        repository.save(convertUpdateTraineeToTrainee(traineeDto));
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

    public void assignTrainerProfileToTraineeProfileByUsernames(String traineeUsername, String trainerUsername) {
        Optional<Trainee> trainee = repository.findByUser_Username(traineeUsername);
        Optional<Trainer> trainer = trainerRepository.findByUser_Username(trainerUsername);

        trainee.ifPresent(tee -> trainer.ifPresent(tr -> tee.getTrainers().add(tr)));
    }

    private Trainee convertUpdateTraineeToTrainee(UpdateTraineeDto traineeDto) {
        return Trainee.builder()
                .user(userService.findUserByUsername(traineeDto.getUsername()).orElse(null))
                .birthDate(traineeDto.getBirthDate())
                .trainers(traineeDto.getTrainerUsernames().stream().map(trainerRepository::findByUser_Username).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .trainings(traineeDto.getTrainingIds().stream().map(trainingRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .id(repository.findByUser_Username(traineeDto.getUsername()).map(Trainee::getId).orElse(null))
                .build();
    }
}
