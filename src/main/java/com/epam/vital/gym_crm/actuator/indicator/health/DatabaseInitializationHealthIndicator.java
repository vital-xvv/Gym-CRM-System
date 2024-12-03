package com.epam.vital.gym_crm.actuator.indicator.health;

import com.epam.vital.gym_crm.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DatabaseInitializationHealthIndicator implements HealthIndicator {

    private final UserRepository userRepository;

    @Override
    public Health health() {
        long count = check();
        if (check() != 0) {
            return Health.up().withDetail("userCount", count).build();
        }
        return Health.down().withDetails(Map.of("message", "Database is not initialized with core data.", "userCount", count)).build();
    }

    private long check() {
        return userRepository.count();
    }
}
