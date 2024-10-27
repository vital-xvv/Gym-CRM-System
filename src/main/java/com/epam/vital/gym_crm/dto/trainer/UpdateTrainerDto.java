package com.epam.vital.gym_crm.dto.trainer;

import com.epam.vital.gym_crm.dict.Specialization;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateTrainerDto {
    @NotBlank
    private String username;
    @NotNull
    private List<String> traineeUsernames;
    @NotNull
    private List<Long> trainingIds;
    @NotNull
    @Size(min = 1)
    private List<Specialization> trainerSpecializations;
}
