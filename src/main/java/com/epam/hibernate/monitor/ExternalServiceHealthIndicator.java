package com.epam.hibernate.monitor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalServiceHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if (isApiHealthy()) {
            return Health.up().withDetail("External Service", "Healthy").build();
        }
        return Health.down().withDetail("External Service", "Is Not-Healthy").build();
    }

    private boolean isApiHealthy() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/actuator", String.class);

            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
