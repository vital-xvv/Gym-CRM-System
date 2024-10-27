package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.GymCrmApplicationTests;
import com.epam.vital.gym_crm.model.Address;
import com.epam.vital.gym_crm.model.User;
import com.epam.vital.gym_crm.repository.AddressRepository;
import com.epam.vital.gym_crm.repository.UserRepository;
import com.epam.vital.gym_crm.util.UserUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Order(1)
    @ParameterizedTest
    @MethodSource("provideUsernameMap")
    public void testSetUserUsername(List<User> users) {
        //before
        repository.findAll().forEach(u -> assertNull(u.getUsername()));

        //when
        users.forEach(service::setUserUsername);

        //then
        repository.findAll().forEach(u -> assertFalse(u.getUsername().isEmpty()));
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("provideUserNamesWhichDuplicate")
    public void testUsernameDuplicatesHandledCorrectly(Map<String, String> duplicateName) {
        //when
        List<List<User>> usersWithDuplicateNames = duplicateName.entrySet().stream().map(e -> repository.findAllByFirstNameAndLastName(e.getKey(), e.getValue())).collect(Collectors.toList());
        System.out.println(usersWithDuplicateNames);

        //then
        usersWithDuplicateNames.forEach(l -> {
            Set<String> usernames = l.stream().map(User::getUsername).collect(Collectors.toSet());
            System.out.println(usernames);
            assertEquals(usernames.size(), l.size());
        });
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("provideUsernames")
    public void testFindUserByUsername(String username) {
        //when
        Optional<User> user= service.findUserByUsername(username);

        //then
        assertTrue(user.isPresent());
        assertEquals(user.get().getUsername(), UserUtils.generateUsername(user.get(), true));
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("provideCorrectAuthCredentials")
    public void testIfAuthenticateUserCorrect(Map<String, String> creds) {
        //then
        creds.forEach((k, v) -> assertTrue(service.authenticateUser(k, v)));
    }

    @Order(5)
    @ParameterizedTest
    @MethodSource("provideIncorrectAuthCredentials")
    public void testIfAuthenticateUserIncorrect(Map<String, String> creds) {
        //then
        creds.forEach((k, v) -> assertFalse(service.authenticateUser(k, v)));
    }

    @Order(6)
    @ParameterizedTest
    @MethodSource("provideChangePasswordCredentials")
    public void testChangePassword(List<String> creds) {
        //before
        service.changeUserProfilePassword(creds.get(0), creds.get(1), creds.get(2));

        //when
        Optional<User> user = service.findUserByUsername(creds.get(0));

        //then
        assertTrue(user.isPresent());
        assertEquals(user.get().getPassword(), creds.get(2));
    }

    @Order(7)
    @ParameterizedTest
    @MethodSource("provideUsernames")
    public void testChangeUserProfileActivation(String username) {
        //before
        Optional<User> user = service.findUserByUsername(username);
        assertTrue(user.isPresent());
        boolean isActive = user.get().getIsActive();

        //when
        service.changeUserProfileActivation(username, !isActive);

        //then
        assertNotEquals(repository.findByUsername(username).get().getIsActive(), isActive);
    }

    @Order(8)
    @ParameterizedTest
    @MethodSource("provideUsernames")
    public void testDeleteUserProfileByUsername(String username) {
        //before
        long beforeCount = repository.count();

        //when
        service.deleteUserProfileByUsername(username);

        //then
        assertEquals(1, beforeCount - repository.count());
    }

    private static Stream<List<User>> provideUsernameMap() {
        return Stream.of(repository.findAll());
    }

    private static Stream<Map<String, String>> provideUserNamesWhichDuplicate() {
        return Stream.of(
                Map.of("Grace", "Martinez", "Eva", "Davis")
        );
    }

    private static Stream<String> provideUsernames() {
        return Stream.of(
                "John.Doe", "Jane.Smith", "Alice.Johnson"
        );
    }

    private static Stream<Map<String, String>> provideCorrectAuthCredentials() {
        return Stream.of(
                Map.of("Henry.Hernandez", "password107", "Alice.Johnson", "password789"),
                Map.of("John.Doe", "password123", "Charlie.Black", "password102")
        );
    }

    private static Stream<Map<String, String>> provideIncorrectAuthCredentials() {
        return Stream.of(
                Map.of("Henry.Hernandez6", "passworffd107", "Alicesad.Johnson", "pas11sword789"),
                Map.of("ggsJohn.Doe", "passwoqqqrd123", "Charlie.78Black", "paafqqssword102")
        );
    }

    private static Stream<List<String>> provideChangePasswordCredentials() {
        return Stream.of(
                List.of("Henry.Hernandez", "password107", "ahahajqkqk"),
                List.of("Alice.Johnson", "password789", "ghheekelele"),
                List.of("John.Doe", "password123", "newPassword"),
                List.of("Charlie.Black", "password102", "testingCompleted")
        );
    }

}
