package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.GymCrmApplicationTests;
import com.epam.vital.gym_crm.model.Address;
import com.epam.vital.gym_crm.model.User;
import com.epam.vital.gym_crm.repository.AddressRepository;
import com.epam.vital.gym_crm.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UserServiceTests extends GymCrmApplicationTests {
    private static final UserService service = applicationContext.getBean(UserService.class);
    private static final UserRepository repository = applicationContext.getBean(UserRepository.class);
    private static final AddressRepository addressRepository = applicationContext.getBean(AddressRepository.class);
    private static final ObjectMapper mapper = applicationContext.getBean(ObjectMapper.class);

    @BeforeAll
    public static void prepareData() {
        try {
            List<Address> addresses = mapper.readValue(new File("src/test/resources/db_init/address.json"), new TypeReference<>() {});
            List<User> users = mapper.readValue(new File("src/test/resources/db_init/users.json"), new TypeReference<>() {});
            addressRepository.saveAll(addresses);
            repository.saveAll(users);
        } catch (Exception e) {
            log.warn("Error occurred while initializing DB data, " +
                    "try to recreate your DB or check if your data files are valid.");
            log.error(e.getMessage());
        }

    }

    @ParameterizedTest
    @MethodSource("provideUsernameMap")
    public void testSetUserUsername(List<User> users) {
        //when
        users.forEach(service::setUserUsername);

        //then
        repository.findAll().forEach(u -> assertFalse(u.getUsername().isEmpty()));
    }

    @ParameterizedTest
    @MethodSource("provideUserNamesWhichDuplicate")
    public void testSetUserUsername(Map<String, String> duplicateName) {
        //when
        List<List<User>> usersWithDuplicateNames = duplicateName.entrySet().stream().map(e -> repository.findAllByFirstNameAndLastName(e.getKey(), e.getValue())).collect(Collectors.toList());

        //then
        usersWithDuplicateNames.forEach(l -> {
            Set<String> usernames = l.stream().map(User::getUsername).collect(Collectors.toSet());
            assertEquals(usernames.size(), l.size());
        });
    }

    private static Stream<List<User>> provideUsernameMap() {
        return Stream.of(repository.findAll());
    }

    // Method providing test data
    private static Stream<Map<String, String>> provideUserNamesWhichDuplicate() {
        return Stream.of(
                Map.of("Grace", "Martinez", "Eva", "Davis")
        );
    }


}
