package com.epam.vital.gym_crm.dto.trainer;

import com.epam.vital.gym_crm.dict.Specialization;
import com.epam.vital.gym_crm.dto.trainee.TraineeProfile;
import com.epam.vital.gym_crm.dto.user.UserProfile;
import com.epam.vital.gym_crm.model.Trainer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrainerProfile {
    private UserProfile userInfo;
    private List<Specialization> specializations;
    private List<TraineeProfile> trainees;

    public static TrainerProfile of(Trainer trainer) {
        return TrainerProfile.builder()
                .userInfo(UserProfile.of(trainer.getUser()))
                .specializations(trainer.getTrainerSpecializations())
                .build();
    }

    public static TrainerProfile ofExtended(Trainer trainer) {
        return TrainerProfile.builder()
                .userInfo(UserProfile.of(trainer.getUser()))
                .specializations(trainer.getTrainerSpecializations())
                .build();
    }
}
