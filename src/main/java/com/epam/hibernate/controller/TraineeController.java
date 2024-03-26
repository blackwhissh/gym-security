package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.trainee.request.TraineeTrainingsRequest;
import com.epam.hibernate.dto.trainee.request.UpdateTraineeRequest;
import com.epam.hibernate.dto.trainee.response.NotAssignedTrainer;
import com.epam.hibernate.dto.trainee.response.TraineeProfileResponse;
import com.epam.hibernate.dto.trainee.response.TraineeTrainingsResponse;
import com.epam.hibernate.dto.trainee.response.UpdateTraineeResponse;
import com.epam.hibernate.service.TraineeService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/trainee", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ROLE_TRAINEE')")
public class TraineeController {
    private final TraineeService traineeService;
    Counter registerCounter;

    public TraineeController(TraineeService traineeService, MeterRegistry meterRegistry) {
        this.traineeService = traineeService;
        this.registerCounter = Counter.builder("trainee_register_counter")
                .description("Number of Hits on Registering Trainee")
                .register(meterRegistry);
    }

    @GetMapping()
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainee Profile", description = "This method returns Logged In Trainee profile")
    public ResponseEntity<TraineeProfileResponse> getCurrentTraineeProfile(@AuthenticationPrincipal String username) {
        return traineeService.selectTraineeProfile(username);
    }

    @PutMapping("/update")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Current Trainee Profile", description = "This method updates Trainee profile")
    public ResponseEntity<UpdateTraineeResponse> updateCurrentTraineeProfile(@AuthenticationPrincipal String username,
                                                                             @RequestBody UpdateTraineeRequest request) {
        return traineeService.updateTrainee(username, request);
    }

    @GetMapping(value = "/not-assigned-trainers")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Not Assigned Trainers To Current Trainee", description = "This method returns Trainers list who are not assigned to this Trainee")
    public ResponseEntity<List<NotAssignedTrainer>> getNotAssignedTrainersCurrent(@AuthenticationPrincipal String username) {
        return traineeService.notAssignedTrainersList(username);
    }

    @GetMapping(value = "/trainings")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Current Trainee's Trainings List", description = "This method returns Trainee's Trainings list")
    public ResponseEntity<List<TraineeTrainingsResponse>> getTrainingsListCurrent(@AuthenticationPrincipal String username,
                                                                                  @RequestBody TraineeTrainingsRequest request) {
        return traineeService.getTrainingList(username, request);
    }

}
