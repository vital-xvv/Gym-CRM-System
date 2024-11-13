package com.epam.vital.gym_crm.dto.address;

import com.epam.vital.gym_crm.model.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    @NotBlank(message = "Street can not be null or empty.")
    private String street;
    @NotBlank(message = "City can not be null or empty.")
    private String city;
    @NotBlank(message = "County code can not be null or empty.")
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
