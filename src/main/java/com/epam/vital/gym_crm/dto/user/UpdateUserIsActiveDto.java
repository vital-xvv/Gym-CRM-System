package com.epam.vital.gym_crm.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserIsActiveDto {
    @NotBlank
    private String username;
    @NotNull
    private Boolean isActive;
}