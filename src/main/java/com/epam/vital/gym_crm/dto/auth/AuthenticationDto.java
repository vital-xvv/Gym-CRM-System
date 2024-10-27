package com.epam.vital.gym_crm.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationDto {
    @NotBlank(message = "Username must not be empty.")
    private String username;
    @NotBlank(message = "Password must not be empty.")
    private String password;
}
