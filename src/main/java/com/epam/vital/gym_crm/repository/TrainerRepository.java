package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUser_Username(String username);
    @Query("select tr from Trainer tr where tr.user.isActive = true and :trainee NOT MEMBER OF tr.trainees")
    List<Trainer> getNotAssignedOnTraineeActiveTrainers(@Param("trainee") Trainee trainee);
}
