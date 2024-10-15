package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository repository;


}
