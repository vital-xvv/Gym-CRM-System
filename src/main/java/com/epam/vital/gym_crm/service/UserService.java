package com.epam.vital.gym_crm.service;

import com.epam.vital.gym_crm.model.User;
import com.epam.vital.gym_crm.repository.UserRepository;
import com.epam.vital.gym_crm.util.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
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

    public boolean changeUserProfilePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent() && authenticateUser(user.get().getUsername(), oldPassword)) {
            user.get().setPassword(newPassword);
            repository.save(user.get());
            return true;
        }
        return false;
    }

    public void changeUserProfileActivation(String username, boolean isActive) {
        Optional<User> user = repository.findByUsername(username);
        user.ifPresent(value -> {
            value.setIsActive(isActive);
            repository.save(user.get());
        });
    }

    public void deleteUserProfileByUsername(String username) {
        repository.deleteByUsername(username);
    }

    public void setUserUsername(User user) {
        user.setUsername(UserUtils.generateUsername(user, !repository.existsByUsername(UserUtils.generateUsername(user, true))));
        repository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return repository.findByUsername(username);
    }

}
