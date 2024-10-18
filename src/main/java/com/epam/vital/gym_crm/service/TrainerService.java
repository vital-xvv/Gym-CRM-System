package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainerService {
    private TrainerRepository repository;

    @Autowired
    public void setRepository(TrainerRepository trainerRepository) {
        repository = trainerRepository;
    }

    public Trainer findTrainerById(Long id) {
        Optional<Trainer> trainer = repository.getById(id);
        if (trainer.isEmpty()) {
            log.error("Trainer with id {} does not exist.", id);
            return null;
        }
        return trainer.get();
    }

    public List<Trainer> getListOfTrainers() {
        return repository.getAllTrainers();
    }

    public boolean createTrainer(Trainer trainer) {
        boolean created = repository.create(trainer);
        if (!created) log.warn("Trainer was not created...");
        return created;
    }

    public boolean updateTrainer(Trainer trainer) {
        boolean updated = repository.update(trainer);
        if (!updated) log.warn("The trainer was not updated...");
        return updated;
    }
}
