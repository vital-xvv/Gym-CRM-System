package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.domain.dto.auth.AuthenticationDto;
import com.epam.vital.gym_crm.domain.dto.auth.ChangePasswordDto;
import com.epam.vital.gym_crm.domain.dto.user.UpdateUserIsActiveDto;
import com.epam.vital.gym_crm.domain.service.UserService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    private static final String BASE_URL = HttpUrlsDict.USER_URL + HttpUrlsDict.CURRENT_VERSION;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Test
    public void testSuccessfulLogin() throws Exception {
        AuthenticationDto validDto = new AuthenticationDto("Oriana.Stinks", "9b633cd9fed44f9ea39ed5113c76f736");
        boolean responseValue = true;
        when(userService.verify(validDto.username(), validDto.password())).thenReturn(Optional.of("fhddkdk"));

        mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(validDto)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("*", responseValue));

        verify(userService, times(1)).verify(validDto.username(), validDto.password());
    }

    @Test
    public void testFailedLogin() throws Exception {
        AuthenticationDto validDto = new AuthenticationDto("Stinks", "9b");
        boolean responseValue = false;
        when(userService.verify(validDto.username(), validDto.password())).thenReturn(Optional.empty());

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("*", responseValue));

        verify(userService, times(1)).verify(validDto.username(), validDto.password());
    }

    @Test
    public void testChangePassword() throws Exception {
        ChangePasswordDto validDto = new ChangePasswordDto("Oriana.Stinks", "9b633cd9fed44f9ea39ed5113c76f736", "EPAM_test_password77!");

        mockMvc.perform(post(BASE_URL + "/change/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto)))
                .andExpect(status().isOk());

        verify(userService, times(1)).changeUserProfilePassword(validDto.username(), validDto.oldPassword(), validDto.newPassword());
    }

    @Test
    public void testManageUserIsActive() throws Exception {
        UpdateUserIsActiveDto validDto = new UpdateUserIsActiveDto("Oriana.Stinks", true);

        mockMvc.perform(post(BASE_URL + "/manage/active")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(validDto)))
                .andExpect(status().isOk());

        verify(userService, times(1)).changeUserProfileActivation(validDto);
    }
}
