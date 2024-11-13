package com.epam.vital.gym_crm.dto.user;

import com.epam.vital.gym_crm.model.Address;
import com.epam.vital.gym_crm.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfile {
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isActive;
    private Address address;

    public static UserProfile of(User user){
        return UserProfile.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .isActive(user.getIsActive())
                .address(user.getAddress())
                .build();
    }
}
