package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.Trainer;
import com.epam.vital.gym_crm.domain.repository.TraineeRepository;
import com.epam.vital.gym_crm.domain.repository.TrainerRepository;
import com.epam.vital.gym_crm.domain.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TraineeServiceTests {
    @Mock
    private TraineeRepository repository;
    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TraineeService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void assignTrainerProfileToTraineeProfileByUsernamesTestSuccess() {
        String traineeUsername = "trainee1";
        String trainerUsername = "trainer1";
        Trainee trainee = new Trainee();
        trainee.setTrainers(Collections.emptyList());

        when(repository.findByUser_Username(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUser_Username(trainerUsername)).thenReturn(Optional.of(new Trainer()));

        service.assignTrainerProfileToTraineeProfileByUsernames(traineeUsername, trainerUsername);

        verify(repository, times(1)).save(any(Trainee.class));
    }


}
