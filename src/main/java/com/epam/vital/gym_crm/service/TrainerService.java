package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository repository;
}
