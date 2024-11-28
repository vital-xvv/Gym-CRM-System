package com.epam.vital.gym_crm.domain.dto.address;

import com.epam.vital.gym_crm.domain.model.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressDto (
    Long id,
    @NotBlank(message = "Street can not be null or empty.")
    String street,
    @NotBlank(message = "City can not be null or empty.")
    String city,
    @NotBlank(message = "County code can not be null or empty.")
    String countryCode,
    String postalCode
) {
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
