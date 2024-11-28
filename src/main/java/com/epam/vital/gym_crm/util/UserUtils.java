package com.epam.vital.gym_crm.util;

import com.epam.vital.gym_crm.domain.model.User;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class UserUtils {

    public static String generateUsername(User user, boolean unique) {
        String username = "%s.%s".formatted(StringUtils.capitalize(user.getFirstName()), StringUtils.capitalize(user.getLastName()));
        return unique ? username : username.concat(UUID.randomUUID().toString().replace("-", ""));
    }

    public static String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
