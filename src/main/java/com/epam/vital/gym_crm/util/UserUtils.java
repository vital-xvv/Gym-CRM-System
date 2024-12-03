package com.epam.vital.gym_crm.util;

import com.epam.vital.gym_crm.domain.model.User;
import static org.springframework.util.StringUtils.capitalize;

import static java.util.UUID.randomUUID;

public class UserUtils {

    public static String generateUsername(User user, boolean unique) {
        String username = "%s.%s".formatted(capitalize(user.getFirstName()), capitalize(user.getLastName()));
        return unique ? username : username.concat(randomUUID().toString().replace("-", ""));
    }

    public static String generateRandomPassword() {
        return randomUUID().toString().replace("-", "");
    }
}
