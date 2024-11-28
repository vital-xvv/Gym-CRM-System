package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.config.WebConfig;
import com.epam.vital.gym_crm.domain.controller.TrainingController;
import com.epam.vital.gym_crm.domain.dict.TrainingType;
import com.epam.vital.gym_crm.domain.dto.training.CreateTrainingDto;
import com.epam.vital.gym_crm.domain.service.TrainingService;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class TrainingControllerTests {

    private static final String BASE_URL = HttpUrlsDict.TRAINING_URL + HttpUrlsDict.CURRENT_VERSION;
    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    @Test
    public void testCreateTraining() throws Exception {
        CreateTrainingDto validDto = new CreateTrainingDto("Training", List.of("Stephan.Smith"), "Jared.Stivens", List.of(TrainingType.CARDIO), LocalDateTime.now(), Duration.ofMinutes(45));

        mockMvc.perform(post(BASE_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto)))
                .andExpect(status().isOk());

        verify(trainingService, times(1)).createTraining(validDto);
    }

    @Test
    public void testFailingCreateTraining() throws Exception {
        CreateTrainingDto validDto = new CreateTrainingDto("", List.of("Stephan.Smith"), "", List.of(TrainingType.CARDIO), LocalDateTime.now(), Duration.ofMinutes(45));

        mockMvc.perform(post(BASE_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest());

        verify(trainingService, times(1)).createTraining(validDto);
    }

    @Test
    public void testGetTrainingTypes() throws Exception {
        mockMvc.perform(get(BASE_URL + "/types"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("*", Arrays.stream(TrainingType.values()).toList()));
    }

}
