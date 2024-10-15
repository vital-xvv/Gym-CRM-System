package com.epam.vital.gym_crm.config;

import com.epam.vital.gym_crm.dict.Specialization;
import com.epam.vital.gym_crm.model.Trainer;
import com.epam.vital.gym_crm.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import one.util.streamex.EntryStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class RepositoryConfig {
    @Value("${users_init_file_path}")
    private String usersInitFilePath;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    @Bean
    public LinkedList<User> userList() throws IOException {
        LinkedList<User> users = objectMapper.readValue(new File(usersInitFilePath), new TypeReference<LinkedList<User>>() {});
        users.forEach(u -> {
            u.setPassword(RandomStringUtils.random(10));
            u.setUsername("%s.%s".formatted(StringUtils.capitalize(u.getFirstName()), StringUtils.capitalize(u.getLastName())));
            if (users.stream().anyMatch(us -> us.getUsername().equals(u.getUsername()))) {
                u.setUsername(u.getUsername() + u.getId());
            }
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
}
