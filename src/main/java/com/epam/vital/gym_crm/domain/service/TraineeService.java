package com.epam.vital.gym_crm.domain.service;

import com.epam.vital.gym_crm.domain.repository.criteria.TraineeCriteriaBuilder;
import com.epam.vital.gym_crm.domain.dto.trainee.CreateTraineeDto;
import com.epam.vital.gym_crm.domain.dto.trainee.TraineeProfile;
import com.epam.vital.gym_crm.domain.dto.trainee.UpdateTraineeDto;
import com.epam.vital.gym_crm.domain.dto.trainer.TrainerProfile;
import com.epam.vital.gym_crm.domain.dto.trainee.TraineeTrainingsDto;
import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.Trainer;
import com.epam.vital.gym_crm.domain.model.Training;
import com.epam.vital.gym_crm.domain.repository.TraineeRepository;
import com.epam.vital.gym_crm.domain.repository.TrainerRepository;
import com.epam.vital.gym_crm.domain.repository.TrainingRepository;
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

    @Transactional
    public boolean deleteTraineeById(Long id) {
        Optional<Trainee> trainee = repository.findById(id);
        List<Training> trainings = trainee.map(trainingRepository::findAllByTraineesContains).orElse(Collections.emptyList());
        trainings.stream().filter(t -> t.getTrainees().size() == 1).forEach(trainingRepository::delete);
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    @Transactional
    public Trainee createTraineeProfile(CreateTraineeDto traineeDto) {
        Trainee trainee = traineeDto.toTrainee();
        userService.initializeUserWithDefaultValues(trainee.getUser());
        return repository.save(trainee);
    }

    @Transactional
    public TraineeProfile updateTraineeProfile(UpdateTraineeDto traineeDto) {
        Trainee trainee = convertUpdateTraineeToTrainee(traineeDto);
        Trainee updatedTrainee = repository.save(trainee);
        return TraineeProfile.of(updatedTrainee);
    }

    public Optional<TraineeProfile> findTraineeProfileByUsername(String username) {
        Optional<Trainee> trainee = repository.findByUser_Username(username);
        return trainee.map(TraineeProfile::ofShort);
    }

    @Transactional
    public void deleteTraineeProfileByUsername(String username) {
        repository.deleteTraineeByUser_Username(username);
    }

    public List<Training> findTraineeTrainingsByCriteria(TraineeTrainingsDto dto) {
        return criteriaBuilder.findAllTraineeTrainingsByCriteria(dto.traineeUsername(), dto.from(), dto.to(), dto.trainerName(), dto.trainingType());
    }

    @Transactional
    public void assignTrainerProfileToTraineeProfileByUsernames(String traineeUsername, String trainerUsername) {
        Optional<Trainee> trainee = repository.findByUser_Username(traineeUsername);
        Optional<Trainer> trainer = trainerRepository.findByUser_Username(trainerUsername);

        trainee.ifPresent(tee -> trainer.ifPresent(tr -> tee.getTrainers().add(tr)));
        trainee.ifPresent(repository::save);
    }

    private Trainee convertUpdateTraineeToTrainee(UpdateTraineeDto traineeDto) {
        return Trainee.builder()
                .user(userService.findUserByUsername(traineeDto.user().username()).orElse(null))
                .birthDate(traineeDto.birthDate())
                .trainers(traineeDto.trainerUsernames().stream().map(trainerRepository::findByUser_Username).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .trainings(traineeDto.trainingIds().stream().map(trainingRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()))
                .id(repository.findByUser_Username(traineeDto.user().username()).map(Trainee::getId).orElse(null))
                .build();
    }

    @Transactional
    public List<TrainerProfile> assignTrainerProfilesToTraineeProfileByUsernames(String traineeUsername, List<String> trainerUsernames) {
        Optional<Trainee> trainee = repository.findByUser_Username(traineeUsername);
        if (trainee.isPresent()) {
            trainerUsernames.forEach(u -> assignTrainerProfileToTraineeProfileByUsernames(traineeUsername, u));
            return repository.findByUser_Username(traineeUsername).get().getTrainers().stream().map(TrainerProfile::of).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
