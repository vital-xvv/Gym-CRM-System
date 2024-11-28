package com.epam.vital.gym_crm.domain.service;

import com.epam.vital.gym_crm.domain.dto.user.UpdateUserIsActiveDto;
import com.epam.vital.gym_crm.domain.model.User;
import com.epam.vital.gym_crm.domain.repository.UserRepository;
import com.epam.vital.gym_crm.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        return user.map(value -> value.getPassword().equals(password)).orElse(false);
    }

    @Transactional
    public void changeUserProfilePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent() && authenticateUser(user.get().getUsername(), oldPassword)) {
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
        user.setPassword(UserUtils.generateRandomPassword());
        user.setIsActive(false);
    }

}
