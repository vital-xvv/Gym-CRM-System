package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.domain.dict.TrainingType;
import com.epam.vital.gym_crm.domain.dto.training.CreateTrainingDto;
import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.Trainer;
import com.epam.vital.gym_crm.domain.model.Training;
import com.epam.vital.gym_crm.domain.repository.TraineeRepository;
import com.epam.vital.gym_crm.domain.repository.TrainerRepository;
import com.epam.vital.gym_crm.domain.repository.TrainingRepository;
import com.epam.vital.gym_crm.domain.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingServiceTests {
    @Mock
    private TrainingRepository repository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private TrainingService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getListOfTrainingsTest() {
        //before
        Training training = new Training();
        training.setId(1L);
        training.setTrainingName("Training 1");
        List<Training> trainings = List.of(training);

        //when
        when(repository.findAll()).thenReturn(trainings);
        List<Training> result = service.getListOfTrainings();

        //then
        assertNotNull(result);
        assertEquals(result.size(), 1);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void createTrainingTestSuccess() {
        CreateTrainingDto dto = new CreateTrainingDto("training2", List.of("trainee1"), "trainer1", List.of(TrainingType.BALANCE_TRAINING), LocalDateTime.now(), Duration.ofMinutes(45));

        when(trainerRepository.findByUser_Username(dto.trainerUsername())).thenReturn(Optional.of(new Trainer()));
        when(traineeRepository.findByUser_Username(dto.traineeUsernames().get(0))).thenReturn(Optional.of(new Trainee()));

        Training training = service.createTraining(dto);

        assertNotNull(training);
        verify(repository, times(1)).save(any(Training.class));
    }

    @Test
    public void createTrainingTestFail() {
        CreateTrainingDto dto = new CreateTrainingDto("training2", List.of("trainee1"), "trainer1", List.of(TrainingType.BALANCE_TRAINING), LocalDateTime.now(), Duration.ofMinutes(45));

        when(trainerRepository.findByUser_Username(dto.trainerUsername())).thenReturn(Optional.empty());
        when(traineeRepository.findByUser_Username(dto.traineeUsernames().get(0))).thenReturn(Optional.empty());

        Training training = service.createTraining(dto);

        assertNull(training);
        verify(repository, times(0)).save(any(Training.class));
    }
}
