package com.epam.vital.gym_crm.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordDto(
    @NotBlank(message = "Username must not be empty.")
    String username,
    @NotBlank(message = "Password must not be empty.")
    String oldPassword,
    @NotBlank(message = "New password must not be empty.")
    @Size(min = 6, max = 20, message = "New password should contain at least 6 letters up to 20.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$", message = "Invalid formation of the password.")
    String newPassword
) {}
