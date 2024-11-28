package com.epam.vital.gym_crm.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto (
    @NotBlank(message = "Username must not be empty.")
    String username,
    @NotBlank(message = "Password must not be empty.")
    String password
) {}
