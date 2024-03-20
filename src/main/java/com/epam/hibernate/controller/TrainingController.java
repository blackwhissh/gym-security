package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.AddTrainingRequest;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.TrainingType;
import com.epam.hibernate.service.TrainingService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.io.NotActiveException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/training", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
public class TrainingController {
    private final TrainingService trainingService;
    Counter addCounter;

    public TrainingController(TrainingService trainingService, MeterRegistry meterRegistry) {
        this.trainingService = trainingService;
        this.addCounter = Counter.builder("training_add_counter")
                .description("Number of Hits on Adding Training")
                .register(meterRegistry);
    }

    @PostMapping(value = "/add")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Add Training", description = "This method adds new  Training")
    public ResponseEntity<?> addTraining(@RequestBody AddTrainingRequest request) throws AccessDeniedException, AuthenticationException, NotActiveException {
        addCounter.increment();
        return trainingService.addTraining(request);
    }

    @GetMapping(value = "/types")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Training Types", description = "This method returns Training Types")
    public ResponseEntity<List<TrainingType>> getTrainingTypes(@RequestBody LoginDTO loginDTO) throws AuthenticationException {
        return trainingService.getTrainingTypes(loginDTO);
    }

}
