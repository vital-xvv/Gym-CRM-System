package com.epam.vital.gym_crm.dto.trainee;

import com.epam.vital.gym_crm.dto.trainer.TrainerProfile;
import com.epam.vital.gym_crm.dto.user.UserProfile;
import com.epam.vital.gym_crm.model.Trainee;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TraineeProfile {
    private UserProfile userInfo;
    private LocalDate birthDate;
    private List<TrainerProfile> trainers;

    public static TraineeProfile of(Trainee trainee) {
        return TraineeProfile.builder()
                .userInfo(UserProfile.of(trainee.getUser()))
                .birthDate(trainee.getBirthDate())
                .trainers(trainee.getTrainers().stream().map(TrainerProfile::of).collect(Collectors.toList()))
                .build();
    }

    public static TraineeProfile ofShort(Trainee trainee) {
        return TraineeProfile.builder()
                .userInfo(UserProfile.of(trainee.getUser()))
                .birthDate(trainee.getBirthDate())
                .build();
    }
}
