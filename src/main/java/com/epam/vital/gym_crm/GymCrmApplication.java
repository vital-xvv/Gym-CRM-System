package com.epam.vital.gym_crm;

import com.epam.vital.gym_crm.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GymCrmApplication implements CommandLineRunner {
    private final TrainerRepository trainerRepository;

    public static void main(String[] args) {
        SpringApplication.run(GymCrmApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(trainerRepository.getAllTrainers());
    }
}
