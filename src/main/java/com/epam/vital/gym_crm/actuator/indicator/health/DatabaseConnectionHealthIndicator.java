package com.epam.vital.gym_crm.actuator.indicator.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DatabaseConnectionHealthIndicator implements HealthIndicator {
    private final DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Health.up().withDetail("Database", "Available").build();
            } else {
                return Health.down().withDetail("Database", "Unavailable").build();
            }
        } catch (SQLException e) {
            return Health.down(e).withDetail("Database", "Error").build();
        }
    }
}
