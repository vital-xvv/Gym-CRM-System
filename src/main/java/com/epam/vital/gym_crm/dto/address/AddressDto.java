package com.epam.vital.gym_crm.dto.address;

import com.epam.vital.gym_crm.model.Address;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressDto {
    private Long id;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String countryCode;
    private String postalCode;

    public Address toAddress() {
        return Address.builder()
                .id(id)
                .street(street)
                .city(city)
                .countryCode(countryCode)
                .postalCode(postalCode)
                .build();
    }
}
