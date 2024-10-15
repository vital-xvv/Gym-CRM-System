package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.model.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainerRepository {
    private final LinkedList<Trainer> trainers;

    public List<Trainer> getAllTrainers() {
        return trainers;
    }
}
