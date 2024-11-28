package com.epam.vital.gym_crm.domain.dto.trainer;

import com.epam.vital.gym_crm.domain.dict.Specialization;
import com.epam.vital.gym_crm.domain.dto.trainee.TraineeProfile;
import com.epam.vital.gym_crm.domain.dto.user.UserProfile;
import com.epam.vital.gym_crm.domain.model.Trainer;
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
