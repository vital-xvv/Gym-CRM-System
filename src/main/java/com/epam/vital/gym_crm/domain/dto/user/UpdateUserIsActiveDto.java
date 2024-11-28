package com.epam.vital.gym_crm.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserIsActiveDto (
    @NotBlank(message = "Username can not be null or empty.")
    String username,
    @NotNull(message = "IsActive can not be null.")
    Boolean isActive
) {}