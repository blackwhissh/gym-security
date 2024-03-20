package com.epam.hibernate.monitor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBHealthIndicator implements HealthIndicator {
    private final JdbcTemplate jdbcTemplate;

    public DBHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        if (isDBHealthy()){
            return Health.up().withDetail("External DB svc", "Healthy").build();
        }
        return Health.down().withDetail("External DB svc", "Is Not-Healthy").build();
    }

    private boolean isDBHealthy(){
        try {
            jdbcTemplate.execute("SELECT 1");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
