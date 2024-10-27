package com.epam.vital.gym_crm.utils;

import com.epam.vital.gym_crm.model.User;
import com.epam.vital.gym_crm.util.UserUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.stream.Stream;

public class UserUtilsTest {

    @ParameterizedTest
    @MethodSource("provideMaps")
    void testGenerateUsernameWhenUnique(Map<String, String> fullNames) {
        //before
        boolean unique = true;

        fullNames.forEach((key, value) -> {
            //when
            User user = User.builder().firstName(key).lastName(value).build();

            //then
            assertEquals("%s.%s".formatted(key, value), UserUtils.generateUsername(user, unique));
        });
    }

    @ParameterizedTest
    @MethodSource("provideMaps")
    void testGenerateUsernameWhenNotUnique(Map<String, String> fullNames) {
        //before
        boolean unique = false;

        fullNames.forEach((key, value) -> {
            //when
            User user = User.builder().firstName(key).lastName(value).build();
            String username = UserUtils.generateUsername(user, unique);

            //then
            assertTrue(username.matches("\\w+\\.[\\w-]+"));
            assertTrue(UserUtils.generateUsername(user, !unique).length() < username.length());
            assertNotEquals(UserUtils.generateUsername(user, !unique), username);
        });
    }

    // Method providing test data
    private static Stream<Map<String, String>> provideMaps() {
        return Stream.of(
                Map.of("Emma", "Johnson", "Liam", "Smith"),
                Map.of("Olivia", "Brown", "Noah", "Davis"),
                Map.of("Ava", "Garcia", "Ethan", "Martinez")
        );
    }

}
