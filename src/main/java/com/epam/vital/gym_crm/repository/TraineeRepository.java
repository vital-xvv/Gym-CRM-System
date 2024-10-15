package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.model.Trainee;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class TraineeRepository {
    private final List<Trainee> traineeList = new LinkedList<>();

    @PostConstruct
    private void initTraineeList() {

    }
}
