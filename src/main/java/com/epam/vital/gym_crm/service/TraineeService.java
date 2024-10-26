package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.repository.TraineeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TraineeService {
    private TraineeRepository repository;

    @Autowired
    public void setRepository(TraineeRepository traineeRepository) {
        repository = traineeRepository;
    }

    public Optional<Trainee> getTraineeById(Long id) {
        Optional<Trainee> trainee = repository.getById(id);
        if (trainee.isEmpty()) log.error("Trainee with id {} does not exist.", id);
        return trainee;
    }

    public List<Trainee> getListOfTrainees() {
        return repository.getAllTrainees();
    }

    public boolean deleteTraineeById(Long id) {
        repository.deleteById(id);
        return repository.getById(id).isEmpty();
    }

    public boolean createTrainee(Trainee trainee) {
        boolean created = repository.create(trainee);
        if (!created) log.error("Trainee was not created...");
        return created;
    }

    public boolean updateTrainee(Trainee trainee) {
        boolean updated = repository.update(trainee);
        if (!updated) log.error("Trainee was not created...");
        return updated;
    }

}
