package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.criteria.TraineeCriteriaBuilder;
import com.epam.vital.gym_crm.dto.trainee.CreateTraineeDto;
import com.epam.vital.gym_crm.dto.trainee.TraineeProfile;
import com.epam.vital.gym_crm.dto.trainee.UpdateTraineeDto;
import com.epam.vital.gym_crm.dto.trainer.TrainerProfile;
import com.epam.vital.gym_crm.dto.trainee.TraineeTrainingsDto;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.repository.TraineeRepository;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import com.epam.vital.gym_crm.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Trainee createTraineeProfile(CreateTraineeDto traineeDto) {
        Trainee trainee = traineeDto.toTrainee();
        userService.initializeUserWithDefaultValues(trainee.getUser());
        return repository.save(trainee);
    }

    public TraineeProfile updateTraineeProfile(UpdateTraineeDto traineeDto) {
        Trainee trainee = convertUpdateTraineeToTrainee(traineeDto);
        return TraineeProfile.of(trainee);
    }

    public Optional<TraineeProfile> findTraineeProfileByUsername(String username) {
        Optional<Trainee> trainee = repository.findByUser_Username(username);
        return trainee.map(TraineeProfile::of);
    }

    public void deleteTraineeProfileByUsername(String username) {
        repository.deleteTraineeByUser_Username(username);
    }

    public List<Training> findTraineeTrainingsByCriteria(TraineeTrainingsDto dto) {
        return criteriaBuilder.findAllTraineeTrainingsByCriteria(dto.getTraineeUsername(), dto.getFrom(), dto.getTo(), dto.getTrainerName(), dto.getTrainingType());
    }

    public void assignTrainerProfileToTraineeProfileByUsernames(String traineeUsername, String trainerUsername) {
        Optional<Trainee> trainee = repository.findByUser_Username(traineeUsername);
        Optional<Trainer> trainer = trainerRepository.findByUser_Username(trainerUsername);

        trainee.ifPresent(tee -> trainer.ifPresent(tr -> tee.getTrainers().add(tr)));
        trainee.ifPresent(repository::save);
    }

    private Trainee convertUpdateTraineeToTrainee(UpdateTraineeDto traineeDto) {
        return Trainee.builder()
                .user(userService.findUserByUsername(traineeDto.getUser().getUsername()).orElse(null))
                .birthDate(traineeDto.getBirthDate())
                .trainers(traineeDto.getTrainerUsernames().stream().map(trainerRepository::findByUser_Username).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .trainings(traineeDto.getTrainingIds().stream().map(trainingRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .id(repository.findByUser_Username(traineeDto.getUser().getUsername()).map(Trainee::getId).orElse(null))
                .build();
    }

    public List<TrainerProfile> assignTrainerProfilesToTraineeProfileByUsernames(String traineeUsername, List<String> trainerUsernames) {
        Optional<Trainee> trainee = repository.findByUser_Username(traineeUsername);
        if (trainee.isPresent()) {
            trainerUsernames.forEach(u -> assignTrainerProfileToTraineeProfileByUsernames(traineeUsername, u));
            return repository.findByUser_Username(traineeUsername).get().getTrainers().stream().map(TrainerProfile::of).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
