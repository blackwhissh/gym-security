package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.trainee.request.TraineeRegisterRequest;
import com.epam.hibernate.dto.trainee.response.TraineeRegisterResponse;
import com.epam.hibernate.dto.trainer.request.TrainerRegisterRequest;
import com.epam.hibernate.dto.trainer.response.TrainerRegisterResponse;
import com.epam.hibernate.service.TraineeService;
import com.epam.hibernate.service.TrainerService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/register", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterController {
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    Counter registerTrainerCounter;
    Counter registerTraineeCounter;

    public RegisterController(TrainerService trainerService, MeterRegistry meterRegistry, TraineeService traineeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.registerTrainerCounter = Counter.builder("trainer_register_counter")
                .description("Number of Hits on Registering Trainer")
                .register(meterRegistry);
        this.registerTraineeCounter = Counter.builder("trainee_register_counter")
                .description("Number of Hits on Registering Trainee")
                .register(meterRegistry);
    }

    @PostMapping(value = "/trainer")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Register Trainer Profile", description = "This method registers Trainer and returns username and password")
    public ResponseEntity<TrainerRegisterResponse> registerTrainer(@RequestBody TrainerRegisterRequest request) {
        registerTraineeCounter.increment();
        return trainerService.createProfile(request);
    }

    @PostMapping(value = "/trainee")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Register Trainee Profile", description = "This method registers Trainee profile and returns " +
            "username and password")
    public ResponseEntity<TraineeRegisterResponse> registerTrainee(@RequestBody TraineeRegisterRequest request) {
        registerTraineeCounter.increment();
        return traineeService.createProfile(request);
    }

}
