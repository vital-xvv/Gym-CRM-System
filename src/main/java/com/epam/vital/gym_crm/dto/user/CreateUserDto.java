package com.epam.vital.gym_crm.dto.user;

import com.epam.vital.gym_crm.dto.address.AddressDto;
import com.epam.vital.gym_crm.model.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateUserDto {
    @NotBlank(message = "First name can not be null or empty.")
    private String firstName;
    @NotBlank(message = "Last name can not be null or empty.")
    private String lastName;
    @NotNull(message = "Address can not be null.")
    @Valid
    private AddressDto address;

    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address.toAddress())
                .build();
    }
}
