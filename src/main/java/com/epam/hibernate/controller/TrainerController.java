package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.trainer.request.TrainerTrainingsRequest;
import com.epam.hibernate.dto.trainer.request.UpdateTrainerRequest;
import com.epam.hibernate.dto.trainer.response.TrainerProfileResponse;
import com.epam.hibernate.dto.trainer.response.TrainerTrainingsResponse;
import com.epam.hibernate.dto.trainer.response.UpdateTrainerResponse;
import com.epam.hibernate.service.TrainerService;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/trainer", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ROLE_TRAINER')")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService, MeterRegistry meterRegistry) {
        this.trainerService = trainerService;
    }

    @GetMapping()
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Current Trainer Profile", description = "This method returns Trainer profile")
    public ResponseEntity<TrainerProfileResponse> getCurrentTrainerProfile(@AuthenticationPrincipal String username) {
        return trainerService.selectTrainerProfile(username);
    }

    @PutMapping(value = "/update")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Current Trainer Profile", description = "This method updates Trainer profile")
    public ResponseEntity<UpdateTrainerResponse> updateTrainerProfile(@AuthenticationPrincipal String username,
                                                                      @RequestBody UpdateTrainerRequest request) {
        return trainerService.updateTrainer(username, request);
    }

    @GetMapping(value = "/trainings/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Current Trainer's Trainings List", description = "This method returns Trainer's Trainings list")
    public ResponseEntity<List<TrainerTrainingsResponse>> getTrainingsListCurrent(@PathVariable String username,
                                                                                  @RequestBody TrainerTrainingsRequest request) {
        return trainerService.getTrainingList(username, request);
    }
}
