package com.epam.vital.gym_crm.dto.user;

import com.epam.vital.gym_crm.dto.address.AddressDto;
import com.epam.vital.gym_crm.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    @NotNull
    private AddressDto address;

    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .address(address.toAddress())
                .build();
    }
}
