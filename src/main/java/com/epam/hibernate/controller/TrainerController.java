package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.trainer.request.TrainerRegisterRequest;
import com.epam.hibernate.dto.trainer.request.TrainerTrainingsRequest;
import com.epam.hibernate.dto.trainer.request.UpdateTrainerRequest;
import com.epam.hibernate.dto.trainer.response.TrainerProfileResponse;
import com.epam.hibernate.dto.trainer.response.TrainerRegisterResponse;
import com.epam.hibernate.dto.trainer.response.TrainerTrainingsResponse;
import com.epam.hibernate.dto.trainer.response.UpdateTrainerResponse;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.service.TrainerService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/trainer", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
public class TrainerController {
    Counter registerCounter;
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService, MeterRegistry meterRegistry) {
        this.trainerService = trainerService;
        this.registerCounter = Counter.builder("trainer_register_counter")
                .description("Number of Hits on Registering Trainer")
                .register(meterRegistry);
    }

    @PostMapping(value = "/register")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Register Trainer Profile", description = "This method registers Trainer and returns username and password")
    public ResponseEntity<TrainerRegisterResponse> registerTrainee(@RequestBody TrainerRegisterRequest request) {
        registerCounter.increment();
        return trainerService.createProfile(request);
    }

    @GetMapping(value = "{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainer Profile", description = "This method returns Trainer profile")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(@PathVariable String username,
                                                                    @RequestBody LoginDTO loginDTO) throws AuthenticationException {
        return trainerService.selectCurrentTrainerProfile(username, loginDTO);
    }

    @PutMapping(value = "/update/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Trainer Profile", description = "This method updates Trainer profile")
    public ResponseEntity<UpdateTrainerResponse> updateTrainerProfile(@PathVariable String username,
                                                                      @RequestBody UpdateTrainerRequest request) throws AuthenticationException {
        return trainerService.updateTrainer(username, request);
    }

    @GetMapping(value = "/trainings/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainer's Trainings List", description = "This method returns Trainer's Trainings list")
    public ResponseEntity<List<TrainerTrainingsResponse>> getTrainingsList(@PathVariable String username,
                                                                           @RequestBody TrainerTrainingsRequest request) throws AuthenticationException {
        return trainerService.getTrainingList(username, request);
    }
}
