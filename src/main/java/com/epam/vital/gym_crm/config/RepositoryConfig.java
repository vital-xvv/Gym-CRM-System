package com.epam.vital.gym_crm.config;

import com.epam.vital.gym_crm.dict.Specialization;
import com.epam.vital.gym_crm.model.Trainee;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.Training;
import com.epam.vital.gym_crm.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import one.util.streamex.EntryStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
public class RepositoryConfig {
    @Value("${users_init_file_path}")
    private String usersInitFilePath;
    @Value("${trainees_init_file_path}")
    private String traineesInitFilePath;
    @Value("${trainings_init_file_path}")
    private String trainingsInitFilePath;

    private ObjectMapper objectMapper;
    private final Random random = new Random(111);
    private final String possiblePasswordChars = "1234567890+-.,/?&*()qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public LinkedList<User> userList() throws IOException {
        LinkedList<User> users = objectMapper.readValue(new File(usersInitFilePath), new TypeReference<LinkedList<User>>() {});
        users.forEach(u -> {
            u.setPassword(new String(RandomStringUtils.random(10, 0, possiblePasswordChars.length(), true, true, possiblePasswordChars.toCharArray(), new Random(123)).getBytes(), StandardCharsets.UTF_8));
            String username = "%s.%s".formatted(StringUtils.capitalize(u.getFirstName()), StringUtils.capitalize(u.getLastName()));
            if (users.stream().anyMatch(us -> username.equals(u.getUsername()))) {
                u.setUsername(username + u.getId());
            } else u.setUsername(username);
        });
        return users;
    }

    @Bean
    LinkedList<Trainer> trainerList(List<User> users) throws IOException {
        List<Integer> ints = List.of(1,6,11,16,21);
        return EntryStream.of(
                ints.stream()
                    .map(id  -> users.stream().filter(u -> id == u.getId().intValue()).findFirst().get())
                    .collect(Collectors.toList()))
                        .map((index) -> new Trainer((long) (index.getKey() + 1), List.of(Specialization.values()[random.nextInt(Specialization.values().length)]), index.getValue().getId()))
                        .collect(Collectors.toCollection(LinkedList::new));
    }

    @Bean
    LinkedList<Trainee> traineeList() throws IOException {
        return objectMapper.readValue(new File(traineesInitFilePath), new TypeReference<LinkedList<Trainee>>() {});
    }

    @Bean
    ArrayList<Training> trainingList() throws IOException {
        return objectMapper.readValue(new File(trainingsInitFilePath), new TypeReference<ArrayList<Training>>() {});
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        return om;
    }
}
