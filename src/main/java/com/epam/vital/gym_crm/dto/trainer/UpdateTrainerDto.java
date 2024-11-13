package com.epam.vital.gym_crm.dto.trainer;

import com.epam.vital.gym_crm.dict.Specialization;
import com.epam.vital.gym_crm.dto.user.UpdateUserDto;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Training;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UpdateTrainerDto {
    @NotNull
    private UpdateUserDto user;
    private List<Long> traineeIds;
    private List<Long> trainingIds;
    @Size(min = 1)
    private List<Specialization> trainerSpecializations;

    public Trainer toTrainer() {
        return Trainer.builder()
                .user(user.toUser())
                .trainerSpecializations(trainerSpecializations)
                .trainings(trainingIds.stream().map(i -> Training.builder().id(i).build()).collect(Collectors.toList()))
                .trainees(traineeIds.stream().map(i -> Trainee.builder().id(i).build()).collect(Collectors.toList()))
                .build();
    }
}
