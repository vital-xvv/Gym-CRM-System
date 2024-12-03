package com.epam.vital.gym_crm.domain.controller;

import com.epam.vital.gym_crm.domain.dto.auth.AuthenticationDto;
import com.epam.vital.gym_crm.domain.dto.auth.ChangePasswordDto;
import com.epam.vital.gym_crm.domain.dto.user.UpdateUserIsActiveDto;
import com.epam.vital.gym_crm.http.util.HttpUrlsDict;
import com.epam.vital.gym_crm.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(HttpUrlsDict.USER_URL + HttpUrlsDict.CURRENT_VERSION)
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
        Optional<String> token = userService.verify(dto.username(), dto.password());
        if (token.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(Map.of("accessToken", token.get()));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "You have been logged out successfully!"));
    }

    @PutMapping("/change/password")
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody ChangePasswordDto dto) {
        userService.changeUserProfilePassword(dto.username(), dto.oldPassword(), dto.newPassword());
        return ResponseEntity.ok().build();
    }
}
