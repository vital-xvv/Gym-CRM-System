package com.epam.vital.gym_crm.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @NotBlank(message = "Username must not be empty.")
    private String username;
    @NotBlank(message = "Password must not be empty.")
    private String oldPassword;
    @NotBlank(message = "New password must not be empty.")
    @Size(min = 6, max = 20, message = "New password should contain at least 6 letters up to 20.")
    private String newPassword;
}
