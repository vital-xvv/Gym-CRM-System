package com.epam.vital.gym_crm.domain.service;

import com.epam.vital.gym_crm.domain.dto.user.UpdateUserIsActiveDto;
import com.epam.vital.gym_crm.domain.model.User;
import com.epam.vital.gym_crm.domain.repository.UserRepository;
import com.epam.vital.gym_crm.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Optional<String> verify(String username, String password) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return authentication.isAuthenticated() ? Optional.of(jwtService.generateToken(username)) : Optional.empty();
    }

    @Transactional
    public void changeUserProfilePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            user.get().setPassword(newPassword);
            repository.save(user.get());
        }
    }

    @Transactional
    public void changeUserProfileActivation(UpdateUserIsActiveDto dto) {
        Optional<User> user = repository.findByUsername(dto.username());
        user.ifPresent(u -> {
            if (u.getIsActive() != dto.isActive()) {
                u.setIsActive(dto.isActive());
                repository.save(user.get());
            }
        });
    }

    @Transactional
    public void deleteUserProfileByUsername(String username) {
        repository.deleteByUsername(username);
    }

    public Optional<User> findUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void initializeUserWithDefaultValues(User user) {
        user.setUsername(UserUtils.generateUsername(user, !repository.existsByUsername(UserUtils.generateUsername(user, true))));
        user.setPassword(passwordEncoder.encode(UserUtils.generateRandomPassword()));
        user.setIsActive(false);
    }

}
