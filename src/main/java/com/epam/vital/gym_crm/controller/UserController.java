package com.epam.vital.gym_crm.controller;

import com.epam.vital.gym_crm.dto.auth.AuthenticationDto;
import com.epam.vital.gym_crm.dto.auth.ChangePasswordDto;
import com.epam.vital.gym_crm.dto.user.UpdateUserIsActiveDto;
import com.epam.vital.gym_crm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/user")
public class UserController {
    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Change user isActive value", description = "Provide a valid UpdateUserIsActiveDto json object to change isActive.")
    @PatchMapping("/manage/active")
    public ResponseEntity<?> manageUserIsActive(@Valid @RequestBody UpdateUserIsActiveDto dto) {
        userService.changeUserProfileActivation(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto dto) {
        return ResponseEntity.ok(userService.authenticateUser(dto.getUsername(), dto.getPassword()));
    }

    @PutMapping("/change/password")
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody ChangePasswordDto dto) {
        userService.changeUserProfilePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
