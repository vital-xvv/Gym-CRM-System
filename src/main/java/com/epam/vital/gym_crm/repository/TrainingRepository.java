package com.epam.vital.gym_crm.repository;

import com.epam.vital.gym_crm.model.Training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> { }
