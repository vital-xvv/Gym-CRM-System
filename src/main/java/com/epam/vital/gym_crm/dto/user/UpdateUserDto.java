package com.epam.vital.gym_crm.dto.user;

import com.epam.vital.gym_crm.dto.address.AddressDto;
import com.epam.vital.gym_crm.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Boolean isActive;
    private AddressDto address;

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
