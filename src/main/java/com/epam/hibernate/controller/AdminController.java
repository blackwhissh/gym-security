package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.trainee.response.TraineeProfileResponse;
import com.epam.hibernate.service.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/admin", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
@EnableMethodSecurity
public class AdminController {
    private final TraineeService traineeService;

    public AdminController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainee/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainee Profile", description = "This method returns Trainee profile")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(@PathVariable String username) {
        return traineeService.selectTraineeProfile(username);
    }

    @DeleteMapping("/delete/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Delete Trainee Profile", description = "This method deletes Trainee profile")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTraineeProfile(@PathVariable String username) {
        traineeService.deleteTrainee(username);
        return ResponseEntity.ok().body("Trainee profile deleted successfully");
    }
}
