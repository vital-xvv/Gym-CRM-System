package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository repository;
}
