package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.dict.TrainingType;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAllByTraineesContains(Trainee trainee);
    //List<Training> getTraineesTrainings(String traineeUsername, LocalDateTime from, LocalDateTime to, String trainerName, TrainingType trainingType);

}
