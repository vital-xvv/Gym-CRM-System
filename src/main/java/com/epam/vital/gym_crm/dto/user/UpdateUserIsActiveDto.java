package com.epam.vital.gym_crm.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserIsActiveDto {
    @NotBlank(message = "Username can not be null or empty.")
    private String username;
    @NotNull(message = "IsActive can not be null.")
    private Boolean isActive;
}