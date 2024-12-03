package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.domain.dto.auth.AuthenticationDto;
import com.epam.vital.gym_crm.domain.dto.trainee.CreateTraineeDto;
import com.epam.vital.gym_crm.domain.dto.trainee.TraineeProfile;
import com.epam.vital.gym_crm.domain.model.Trainee;
import com.epam.vital.gym_crm.domain.model.User;
import com.epam.vital.gym_crm.domain.service.TraineeService;
import com.epam.vital.gym_crm.http.util.HttpUrlsDict;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TraineeControllerTests {
    private static final String BASE_URL = HttpUrlsDict.TRAINEE_URL + HttpUrlsDict.CURRENT_VERSION;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TraineeService traineeService;

    @Test
    public void testRegsiterTraineeFail() throws Exception {
        String invalidJson = """
                {
                    "user" : {
                        "firstName" : "Oggy",
                        "lastName" : "Redcliff",
                        "address" : {}
                    },
                    "birthDate": "1999-12-10"
                }""";

        CreateTraineeDto dto = om.readValue(invalidJson, CreateTraineeDto.class);

        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect((ResultMatcher) jsonPath("$.message", is("City can not be null or empty.")));

        verify(traineeService, times(0)).createTraineeProfile(dto);
    }

    @Test
    public void testRegsiterTraineeSuccessful() throws Exception {
        String validJson = """
                {
                    "user" : {
                        "firstName" : "Oggy",
                        "lastName" : "Redcliff",
                        "address" : {
                            "street": "456 Maple Ave",
                            "city": "Los Angeles",
                            "countryCode": "US",
                            "postalCode": "90001"
                        }
                    },
                    "birthDate": "1999-12-10"
                }""";
        String jsonResponse = """
                {
                    "username": "Oggy.Redcliff",
                    "password": "c88cccf331c94355a0b31a9e5d4a640a"
                }
                """;

        CreateTraineeDto dto = om.readValue(validJson, CreateTraineeDto.class);
        AuthenticationDto authDto = om.readValue(jsonResponse, AuthenticationDto.class);
        Trainee trainee = new Trainee();
        User user = new User();
        user.setUsername(authDto.username());
        user.setPassword(authDto.password());
        trainee.setUser(user);

        when(traineeService.createTraineeProfile(dto)).thenReturn(trainee);


        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.username", is("Oggy.Redcliff")))
                .andExpect(jsonPath("$.password").exists());

        verify(traineeService, times(1)).createTraineeProfile(dto);
    }

    @Test
    public void testGetTraineeByUsernameSuccessful() throws Exception {
        String traineeUsername = "Oggy.Redcliff";
        String validJsonResponse = """
                    {
                         "userInfo": {
                             "firstName": "Oggy",
                             "lastName": "Redcliff",
                             "username": "Oggy.Redcliff",
                             "isActive": false,
                             "address": {
                                 "id": 103,
                                 "street": "456 Maple Ave",
                                 "city": "Los Angeles",
                                 "countryCode": "US",
                                 "postalCode": "90001"
                             }
                         },
                         "birthDate": [
                             1999,
                             12,
                             10
                         ],
                         "trainers": null
                     }
                """;
        TraineeProfile profile = om.readValue(validJsonResponse, TraineeProfile.class);
        when(traineeService.findTraineeProfileByUsername(traineeUsername)).thenReturn(Optional.of(profile));


        mockMvc.perform(get(BASE_URL + "/" + traineeUsername))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainers").isEmpty())
                .andExpect((ResultMatcher) jsonPath("$.userInfo.firstName", is(profile.getUserInfo().getFirstName())))
                .andExpect((ResultMatcher) jsonPath("$.userInfo.isActive", is(profile.getUserInfo().getIsActive())));

        verify(traineeService, times(1)).findTraineeProfileByUsername(traineeUsername);
    }

    @Test
    public void testGetTraineeByUsernameEmpty() throws Exception {
        String traineeUsername = "Oggy.Redclif";

        when(traineeService.findTraineeProfileByUsername(traineeUsername)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + "/" + traineeUsername))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainers").isEmpty())
                .andExpect(jsonPath("$.userInfo").doesNotExist());

        verify(traineeService, times(1)).findTraineeProfileByUsername(traineeUsername);
    }

    @Test
    public void testDeleteTraineeByUsername() throws Exception {
        String traineeUsername = "Oggy.Redcliff";

        mockMvc.perform(get(BASE_URL + "/delete" + "/" + traineeUsername))
                .andExpect(status().isOk());

        verify(traineeService, times(1)).deleteTraineeProfileByUsername(traineeUsername);
    }



}
