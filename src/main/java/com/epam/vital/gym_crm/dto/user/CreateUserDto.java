package com.epam.vital.gym_crm.dto.user;

import com.epam.vital.gym_crm.dto.address.AddressDto;
import com.epam.vital.gym_crm.model.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateUserDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private AddressDto address;

    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address.toAddress())
                .build();
    }
}
