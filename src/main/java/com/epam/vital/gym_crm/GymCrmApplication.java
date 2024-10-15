package com.epam.vital.gym_crm;

import com.epam.vital.gym_crm.repository.TraineeRepository;
import com.epam.vital.gym_crm.repository.TrainerRepository;
import com.epam.vital.gym_crm.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GymCrmApplication implements CommandLineRunner {
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;

    public static void main(String[] args) {
        SpringApplication.run(GymCrmApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(trainerRepository.getAllTrainers());
        System.out.println(traineeRepository.getAllTrainees());
        System.out.println(trainingRepository.getAllTrainings());
    }
}
