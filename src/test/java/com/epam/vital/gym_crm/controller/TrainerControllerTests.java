package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.config.WebConfig;
import com.epam.vital.gym_crm.domain.controller.TrainerController;
import com.epam.vital.gym_crm.domain.dict.Specialization;
import com.epam.vital.gym_crm.domain.dto.trainer.CreateTrainerDto;
import com.epam.vital.gym_crm.domain.dto.trainer.TrainerProfile;
import com.epam.vital.gym_crm.domain.dto.trainer.UpdateTrainerDto;
import com.epam.vital.gym_crm.domain.service.TrainerService;
import com.epam.vital.gym_crm.http.util.HttpUrlsDict;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class TrainerControllerTests {

    private static final String BASE_URL = HttpUrlsDict.TRAINER_URL + HttpUrlsDict.CURRENT_VERSION;
    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
    }

    @Test
    public void testRegisterTrainer() throws Exception {
        String validDtoJson = """
                {
                    "user" : {
                        "firstName" : "Oriana",
                        "lastName" : "Stinks",
                        "address" : {}
                    },
                    "trainerSpecializations" : ["BODYBUILDING_COACH"]
                }
                """;
        CreateTrainerDto validDto = om.readValue(validDtoJson, CreateTrainerDto.class);

        when(trainerService.createTrainerProfile(validDto)).thenReturn(validDto.toTrainer());

        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("trainerSpecializations", List.of(Specialization.BODYBUILDING_COACH)));

        verify(trainerService, times(1)).createTrainerProfile(validDto);
    }

    @Test
    public void testFailingRegisterTrainer() throws Exception {
        String validDtoJson = """
                {
                    "user" : {
                        "firstName" : "",
                        "lastName" : "Stinks",
                        "address" : {}
                    },
                    "trainerSpecializations" : []
                }
                """;
        CreateTrainerDto validDto = om.readValue(validDtoJson, CreateTrainerDto.class);

        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTrainerByUsername() throws Exception {
        String trainerUsername = "Oriana.Stinks";
        String validJsonResponse = """
                {
                    "userInfo": {
                        "firstName": "Oriana",
                        "lastName": "Stinks",
                        "username": "Oriana.Stinks",
                        "isActive": false,
                        "address": {
                            "id": 1,
                            "street": null,
                            "city": null,
                            "countryCode": null,
                            "postalCode": null
                        }
                    },
                    "specializations": [
                        "BODYBUILDING_COACH"
                    ],
                    "trainees": null
                }
                """;
        TrainerProfile profile = om.readValue(validJsonResponse, TrainerProfile.class);

        mockMvc.perform(get(BASE_URL + "/" + trainerUsername))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("specializations", profile.getSpecializations()));

        verify(trainerService, times(1)).findTrainerProfileByUsername(trainerUsername);
    }

    @Test
    public void testUpdateTrainerSuccess() throws Exception {
        String validJsonDto = """
                {
                    "user" : {
                        "username" : "Oriana.Stinks",
                        "firstName" : "Maddy",
                        "lastName" : "Stone",
                        "address": {
                            "street": "123 Main St",
                            "city": "New York",
                            "countryCode": "US",
                            "postalCode": "10001"
                        },
                        "isActive" : true
                                
                    },
                    "trainerSpecializations" : ["BODYBUILDING_COACH"],
                    "traineeIds" : [2],
                    "trainingIds": []
                }""";

        String jsonResponse = """
                {
                    "userInfo": {
                        "firstName": "Maddy",
                        "lastName": "Stone",
                        "username": "Oriana.Stinks",
                        "isActive": true,
                        "address": {
                            "id": 102,
                            "street": "123 Main St",
                            "city": "New York",
                            "countryCode": "US",
                            "postalCode": "10001"
                        }
                    },
                    "specializations": [
                        "BODYBUILDING_COACH"
                    ],
                    "trainees": null
                }""";

        UpdateTrainerDto dto = om.readValue(validJsonDto, UpdateTrainerDto.class);
        TrainerProfile profile = om.readValue(jsonResponse, TrainerProfile.class);

        mockMvc.perform(post(BASE_URL + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.userInfo.firstName", is(profile.getUserInfo().getFirstName())))
                .andExpect((ResultMatcher) jsonPath("$.userInfo.lastName", is(profile.getUserInfo().getLastName())))
                .andExpect((ResultMatcher) jsonPath("$.userInfo.isActive", is(profile.getUserInfo().getIsActive())))
                .andExpect((ResultMatcher) jsonPath("$.specializations", is(profile.getSpecializations())));

        verify(trainerService, times(1)).updateTrainerProfile(dto);
    }

    @Test
    public void testUpdateTrainerFail() throws Exception {
        String invalidJsonDto = """
                {
                    "user" : {
                        "username" : "Oriana.Stinks",
                        "firstName" : "",
                        "lastName" : "",
                        "address": {
                            "street": "",
                            "city": "New York",
                            "countryCode": "US",
                            "postalCode": "10001"
                        },
                        "isActive" : true
                                
                    },
                    "trainerSpecializations" : ["BODYBUILDING_COACH"],
                    "traineeIds" : [2],
                    "trainingIds": []
                }""";

        UpdateTrainerDto dto = om.readValue(invalidJsonDto, UpdateTrainerDto.class);

        mockMvc.perform(post(BASE_URL + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(trainerService, times(0)).updateTrainerProfile(dto);
    }

    @Test
    public void testGetNotAssignedTrainersOnTraineeEmpty() throws Exception {
        String traineeUsername = "Bob";

        mockMvc.perform(get(BASE_URL + "/not/assigned/trainee/" + traineeUsername))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(trainerService, times(1)).getTrainersWithoutTraineeByUsername(traineeUsername);
    }

    @Test
    public void testGetNotAssignedTrainersOnTraineeSuccess() throws Exception {
        String traineeUsername = "Oriana.Stinks";

        mockMvc.perform(get(BASE_URL + "/not/assigned/trainee/" + traineeUsername))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(trainerService, times(1)).getTrainersWithoutTraineeByUsername(traineeUsername);
    }
}
