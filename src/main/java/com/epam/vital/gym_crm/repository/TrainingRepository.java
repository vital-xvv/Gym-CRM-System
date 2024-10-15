package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.model.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainingRepository {
    private final List<Training> trainingList;

    public List<Training> getAllTrainings() {
        return trainingList;
    }

    public Optional<Training> getById(Long id) {
        return trainingList.stream().filter(t -> t.getId().longValue() == id.longValue()).findFirst();
    }

    public boolean create(Training training) {
        if(training.getId() != null && trainingList.stream().anyMatch(t -> t.getId().longValue() == training.getId().longValue()))
//            throw new IllegalArgumentException("An entity with id %d already exists.".formatted(training.getId()));
            return false;
        training.setId(trainingList.get(trainingList.size() - 1).getId() + 1);
        return trainingList.add(training);
    }
}
