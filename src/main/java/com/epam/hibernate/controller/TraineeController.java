package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.trainee.request.TraineeRegisterRequest;
import com.epam.hibernate.dto.trainee.request.TraineeTrainingsRequest;
import com.epam.hibernate.dto.trainee.request.UpdateTraineeRequest;
import com.epam.hibernate.dto.trainee.request.UpdateTrainersListRequest;
import com.epam.hibernate.dto.trainee.response.*;
import com.epam.hibernate.dto.trainer.TrainerListInfo;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.service.TraineeService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/trainee", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
public class TraineeController {
    private final TraineeService traineeService;
    Counter registerCounter;

    public TraineeController(TraineeService traineeService, MeterRegistry meterRegistry) {
        this.traineeService = traineeService;
        this.registerCounter = Counter.builder("trainee_register_counter")
                .description("Number of Hits on Registering Trainee")
                .register(meterRegistry);
    }

    @PostMapping(value = "/register")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Register Trainee Profile", description = "This method registers Trainee profile and returns " +
            "username and password")
    public ResponseEntity<TraineeRegisterResponse> registerTrainee(@RequestBody TraineeRegisterRequest request) {
        registerCounter.increment();
        return traineeService.createProfile(request);
    }

    @GetMapping()
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainee Profile", description = "This method returns Logged In Trainee profile")
    @PreAuthorize("hasRole('ROLE_TRAINEE')")
    public ResponseEntity<TraineeProfileResponse> getCurrentTraineeProfile(@AuthenticationPrincipal String username) throws AuthenticationException {
        return traineeService.selectTraineeProfile(username);
    }

    @PutMapping("/update/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Trainee Profile", description = "This method updates Trainee profile")
    @PreAuthorize("hasRole('ROLE_TRAINEE')")
    public ResponseEntity<UpdateTraineeResponse> updateTraineeProfile(@PathVariable String username,
                                                                      @RequestBody UpdateTraineeRequest request) throws AuthenticationException {
        return traineeService.updateTrainee(username, request);
    }

    @GetMapping(value = "/not-assigned-trainers/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Not Assigned Trainers", description = "This method returns Trainers list who are not assigned to this Trainee")
    public ResponseEntity<List<NotAssignedTrainer>> getNotAssignedTrainers(@PathVariable String username,
                                                                           @RequestBody LoginDTO loginDTO) throws AuthenticationException {
        return traineeService.notAssignedTrainersList(username, loginDTO);
    }

    @PutMapping(value = "/update-trainers/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Trainee's Trainers List", description = "This method returns Trainee's Trainers profile")
    public ResponseEntity<List<TrainerListInfo>> updateTrainersList(@PathVariable String username,
                                                                    @RequestBody UpdateTrainersListRequest request) throws AuthenticationException {
        return traineeService.updateTrainersList(username, request);
    }

    @GetMapping(value = "/trainings/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainee's Trainings List", description = "This method returns Trainee's Trainings list")
    public ResponseEntity<List<TraineeTrainingsResponse>> getTrainingsList(@PathVariable String username,
                                                                           @RequestBody TraineeTrainingsRequest request) throws AuthenticationException {
        return traineeService.getTrainingList(username, request);
    }

}
