package com.epam.vital.gym_crm.domain.dto.user;

import com.epam.vital.gym_crm.domain.dto.address.AddressDto;
import com.epam.vital.gym_crm.domain.model.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto (
    @NotBlank(message = "First name can not be null or empty.")
    String firstName,
    @NotBlank(message = "Last name can not be null or empty.")
    String lastName,
    @NotNull(message = "Address can not be null.")
    @Valid
    AddressDto address
) {
    public User toUser() {
        return User.builder()
            .firstName(firstName)
            .lastName(lastName)
            .address(address.toAddress())
            .build();
    }
}
