package com.epam.vital.gym_crm.domain.service;

import com.epam.vital.gym_crm.domain.repository.criteria.TrainerCriteriaBuilder;
import com.epam.vital.gym_crm.domain.dto.trainer.CreateTrainerDto;
import com.epam.vital.gym_crm.domain.dto.trainer.TrainerProfile;
import com.epam.vital.gym_crm.domain.dto.trainer.TrainerTrainingsDto;
import com.epam.vital.gym_crm.domain.dto.trainer.UpdateTrainerDto;
import com.epam.vital.gym_crm.domain.model.Trainer;
import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.Training;
import com.epam.vital.gym_crm.domain.repository.AddressRepository;
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
public class TrainerService {
    private final TrainerRepository repository;
    private final TraineeRepository traineeRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final TrainerCriteriaBuilder criteriaBuilder;

    @Autowired
    public TrainerService(TrainerRepository repository, TraineeRepository traineeRepository, UserService userService, TrainerCriteriaBuilder criteriaBuilder, AddressRepository addressRepository) {
        this.repository = repository;
        this.traineeRepository = traineeRepository;
        this.userService = userService;
        this.criteriaBuilder = criteriaBuilder;
        this.addressRepository = addressRepository;
    }

    public List<Trainer> getTrainerProfiles() {
        return repository.findAll();
    }

    @Transactional
    public Trainer createTrainerProfile(CreateTrainerDto trainerDto) {
        Trainer trainer = trainerDto.toTrainer();
        userService.initializeUserWithDefaultValues(trainer.getUser());
        return repository.save(trainer);
    }

    public Optional<TrainerProfile> findTrainerProfileByUsername(String username) {
        Optional<Trainer> trainer = repository.findByUser_Username(username);
        return trainer.map(TrainerProfile::ofExtended);
    }

    @Transactional
    public TrainerProfile updateTrainerProfile(UpdateTrainerDto trainerDto) {
        Trainer trainer = trainerDto.toTrainer();
        Optional<Trainer> existingTrainer = repository.findByUser_Username(trainer.getUser().getUsername());
        if (existingTrainer.isPresent()) {
            Trainer t = existingTrainer.get();
            t.getUser().setFirstName(trainerDto.user().firstName());
            t.getUser().setLastName(trainerDto.user().lastName());
            t.getUser().setIsActive(trainerDto.user().isActive());
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
        return criteriaBuilder.findAllTrainerTrainingsByCriteria(dto.trainerUsername(), dto.from(), dto.to(), dto.traineeName());
    }

    public List<TrainerProfile> getTrainersWithoutTraineeByUsername(String traineeUsername) {
        Optional<Trainee> trainee = traineeRepository.findByUser_Username(traineeUsername);
        return trainee.map( t ->
            repository.getNotAssignedOnTraineeActiveTrainers(t).stream().map(TrainerProfile::of).toList()
        ).orElse(Collections.emptyList());
    }
}
