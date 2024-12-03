package com.epam.vital.gym_crm.domain.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BruteForceProtectionService {

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCKOUT_DURATION_MINUTES = 5;

    private final Map<String, LoginAttempt> attempts = new HashMap<>();

    public void recordFailedAttempt(String key) {
        LoginAttempt attempt = attempts.getOrDefault(key, new LoginAttempt(0, null));
        attempt.incrementAttempts();
        if (attempt.getAttempts() >= MAX_ATTEMPTS) {
            attempt.setLockoutTime(LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES));
        }
        attempts.put(key, attempt);
    }

    public boolean isBlocked(String key) {
        LoginAttempt attempt = attempts.get(key);
        if (attempt == null) {
            return false;
        }
        if (attempt.getLockoutTime() != null && LocalDateTime.now().isBefore(attempt.getLockoutTime())) {
            return true;
        }
        if (attempt.getLockoutTime() != null && LocalDateTime.now().isAfter(attempt.getLockoutTime())) {
            attempts.remove(key);
        }
        return false;
    }

    public void resetAttempts(String key) {
        attempts.remove(key);
    }

    private static class LoginAttempt {
        private int attempts;
        private LocalDateTime lockoutTime;

        public LoginAttempt(int attempts, LocalDateTime lockoutTime) {
            this.attempts = attempts;
            this.lockoutTime = lockoutTime;
        }

        public int getAttempts() {
            return attempts;
        }

        public void incrementAttempts() {
            this.attempts++;
        }

        public LocalDateTime getLockoutTime() {
            return lockoutTime;
        }

        public void setLockoutTime(LocalDateTime lockoutTime) {
            this.lockoutTime = lockoutTime;
        }
    }
}
