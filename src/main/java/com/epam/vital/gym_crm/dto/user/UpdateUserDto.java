package com.epam.vital.gym_crm.dto.user;

import com.epam.vital.gym_crm.dto.address.AddressDto;
import com.epam.vital.gym_crm.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotBlank(message = "Username can not be null or empty.")
    private String username;
    @NotBlank(message = "First name can not be null or empty.")
    private String firstName;
    @NotBlank(message = "Last name can not be null or empty.")
    private String lastName;
    @NotNull(message = "IsActive can not be null.")
    private Boolean isActive;
    @Valid
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
