package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.criteria.TrainerCriteriaBuilder;
import com.epam.vital.gym_crm.dict.TrainingType;
import com.epam.vital.gym_crm.dto.trainer.CreateTrainerDto;
import com.epam.vital.gym_crm.dto.trainer.TrainerProfile;
import com.epam.vital.gym_crm.dto.trainer.TrainerTrainingsDto;
import com.epam.vital.gym_crm.dto.trainer.UpdateTrainerDto;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.repository.AddressRepository;
import com.epam.vital.gym_crm.repository.TraineeRepository;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import com.epam.vital.gym_crm.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
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
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final TrainerCriteriaBuilder criteriaBuilder;

    @Autowired
    public TrainerService(TrainerRepository repository, TraineeRepository traineeRepository, TrainingRepository trainingRepository, UserService userService, TrainerCriteriaBuilder criteriaBuilder, AddressRepository addressRepository) {
        this.repository = repository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.userService = userService;
        this.criteriaBuilder = criteriaBuilder;
        this.addressRepository = addressRepository;
    }

    public List<Trainer> getTrainerProfiles() {
        return repository.findAll();
    }

    public Trainer createTrainerProfile(CreateTrainerDto trainerDto) {
        Trainer trainer = trainerDto.toTrainer();
        userService.initializeUserWithDefaultValues(trainer.getUser());
        return repository.save(trainer);
    }

    public Optional<TrainerProfile> findTrainerProfileByUsername(String username) {
        Optional<Trainer> trainer = repository.findByUser_Username(username);
        return trainer.map(TrainerProfile::ofExtended);
    }

    public void changeTrainerProfilePassword(String username, String oldPassword, String newPassword) {
        userService.changeUserProfilePassword(username, oldPassword, newPassword);
    }

    public TrainerProfile updateTrainerProfile(UpdateTrainerDto trainerDto) {
        Trainer trainer = trainerDto.toTrainer();
        Optional<Trainer> existingTrainer = repository.findByUser_Username(trainer.getUser().getUsername());
        if (existingTrainer.isPresent()) {
            Trainer t = existingTrainer.get();
            t.getUser().setFirstName(trainerDto.getUser().getFirstName());
            t.getUser().setLastName(trainerDto.getUser().getLastName());
            t.getUser().setIsActive(trainerDto.getUser().getIsActive());
            if (trainer.getUser().getAddress() != null) {
                if (addressRepository.findById(t.getUser().getAddress().getId()).isPresent())
                    t.getUser().setAddress(trainer.getUser().getAddress());
            }
            if (trainer.getTrainerSpecializations() != null)
                t.setTrainerSpecializations(trainer.getTrainerSpecializations());
            if (trainer.getTrainees() != null)
                t.setTrainees(trainer.getTrainees().stream().map(trn -> traineeRepository.findById(trn.getId())).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()));

            return TrainerProfile.ofExtended(repository.save(t));
        }
        return TrainerProfile.builder().build();
    }

    public List<Training> getTrainerTrainingsByCriteria(TrainerTrainingsDto dto) {
        return criteriaBuilder.findAllTrainerTrainingsByCriteria(dto.getTrainerUsername(), dto.getFrom(), dto.getTo(), dto.getTraineeName());
    }

    public List<TrainerProfile> getTrainersWithoutTraineeByUsername(String traineeUsername) {
        Optional<Trainee> trainee = traineeRepository.findByUser_Username(traineeUsername);
        return trainee.map( t ->
            repository.getNotAssignedOnTraineeActiveTrainers(t).stream().map(TrainerProfile::of).toList()
        ).orElse(Collections.emptyList());
    }
}
