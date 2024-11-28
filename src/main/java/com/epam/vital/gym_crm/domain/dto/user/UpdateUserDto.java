package com.epam.vital.gym_crm.domain.dto.user;

import com.epam.vital.gym_crm.domain.dto.address.AddressDto;
import com.epam.vital.gym_crm.domain.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDto (
    @NotBlank(message = "Username can not be null or empty.")
    String username,
    @NotBlank(message = "First name can not be null or empty.")
    String firstName,
    @NotBlank(message = "Last name can not be null or empty.")
    String lastName,
    @NotNull(message = "IsActive can not be null.")
    Boolean isActive,
    @Valid
    AddressDto address
) {
    public User toUser() {
        return User.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive)
                .address(address.toAddress())
                .build();
    }
}