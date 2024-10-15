package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.model.Trainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrainerRepository {
    private final LinkedList<Trainer> trainers;

    public List<Trainer> getAllTrainers() {
        return trainers;
    }

    public Optional<Trainer> getById(Long id) {
        return trainers.stream().filter(t -> t.getId().longValue() == id.longValue()).findFirst();
    }

    public boolean create(Trainer trainer) {
        if (trainer.getId() != null && trainers.stream().anyMatch(t -> t.getId().longValue() == trainer.getId().longValue()))
            return false;
        trainer.setId(trainers.get(trainers.size() - 1).getId() + 1);
        return trainers.add(trainer);
    }

    public boolean update(Trainer trainer) {
        Optional<Trainer> trainerOptional = getById(trainer.getId());
        if (trainerOptional.isEmpty()) {
            log.error("An entity with id %d already exists.".formatted(trainer.getId()));
            return false;
        }

        Trainer trainerToUpdate = trainerOptional.get();
        trainerToUpdate.setTrainerSpecializations(trainer.getTrainerSpecializations());
        trainerToUpdate.setUserId(trainer.getUserId());
        return true;
    }
}
