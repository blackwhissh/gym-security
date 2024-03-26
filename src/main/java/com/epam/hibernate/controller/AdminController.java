package com.epam.hibernate.controller;

import com.epam.hibernate.config.LogEntryExit;
import com.epam.hibernate.dto.trainee.request.TraineeTrainingsRequest;
import com.epam.hibernate.dto.trainee.request.UpdateTraineeRequest;
import com.epam.hibernate.dto.trainee.request.UpdateTrainersListRequest;
import com.epam.hibernate.dto.trainee.response.NotAssignedTrainer;
import com.epam.hibernate.dto.trainee.response.TraineeProfileResponse;
import com.epam.hibernate.dto.trainee.response.TraineeTrainingsResponse;
import com.epam.hibernate.dto.trainee.response.UpdateTraineeResponse;
import com.epam.hibernate.dto.trainer.TrainerListInfo;
import com.epam.hibernate.dto.trainer.request.TrainerTrainingsRequest;
import com.epam.hibernate.dto.trainer.request.UpdateTrainerRequest;
import com.epam.hibernate.dto.trainer.response.TrainerProfileResponse;
import com.epam.hibernate.dto.trainer.response.TrainerTrainingsResponse;
import com.epam.hibernate.dto.trainer.response.UpdateTrainerResponse;
import com.epam.hibernate.service.TraineeService;
import com.epam.hibernate.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/admin", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final TrainerService trainerService;
    private final TraineeService traineeService;

    public AdminController(TrainerService trainerService, TraineeService traineeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    @GetMapping("/trainee/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainee Profile", description = "This method returns Trainee profile")
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(@PathVariable String username) {
        return traineeService.selectTraineeProfile(username);
    }

    @DeleteMapping("/delete/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Delete Trainee Profile", description = "This method deletes Trainee profile")
    public ResponseEntity<?> deleteTraineeProfile(@PathVariable String username) {
        traineeService.deleteTrainee(username);
        return ResponseEntity.ok().body("Trainee profile deleted successfully");
    }

    @PutMapping("/update/trainee/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Trainee Profile", description = "This method updates Trainee profile")
    public ResponseEntity<UpdateTraineeResponse> updateTraineeProfile(@PathVariable String username,
                                                                      @RequestBody UpdateTraineeRequest request) {
        return traineeService.updateTrainee(username, request);
    }

    @GetMapping(value = "/not-assigned-trainers/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Not Assigned Trainers", description = "This method returns Trainers list who are not assigned to this Trainee")
    public ResponseEntity<List<NotAssignedTrainer>> getNotAssignedTrainers(@PathVariable String username) {
        return traineeService.notAssignedTrainersList(username);
    }

    @PutMapping(value = "/update-trainers/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Trainee's Trainers List", description = "This method returns Trainee's Trainers profile")
    public ResponseEntity<List<TrainerListInfo>> updateTrainersList(@PathVariable String username,
                                                                    @RequestBody UpdateTrainersListRequest request) {
        return traineeService.updateTrainersList(username, request);
    }

    @GetMapping(value = "/trainings/trainee/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainee's Trainings List", description = "This method returns Trainee's Trainings list")
    public ResponseEntity<List<TraineeTrainingsResponse>> getTrainingsList(@PathVariable String username,
                                                                           @RequestBody TraineeTrainingsRequest request) {
        return traineeService.getTrainingList(username, request);
    }

    @GetMapping(value = "/trainer/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainer Profile", description = "This method returns Trainer profile")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(@PathVariable String username) {
        return trainerService.selectTrainerProfile(username);
    }

    @PutMapping(value = "/update/trainer/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Update Trainer Profile", description = "This method updates Trainer profile")
    public ResponseEntity<UpdateTrainerResponse> updateTrainerProfile(@PathVariable String username,
                                                                      @RequestBody UpdateTrainerRequest request) {
        return trainerService.updateTrainer(username, request);
    }

    @GetMapping(value = "/trainings/trainer/{username}")
    @LogEntryExit(showArgs = true, showResult = true)
    @Operation(summary = "Get Trainer's Trainings List", description = "This method returns Trainer's Trainings list")
    public ResponseEntity<List<TrainerTrainingsResponse>> getTrainingsList(@PathVariable String username,
                                                                           @RequestBody TrainerTrainingsRequest request) {
        return trainerService.getTrainingList(username, request);
    }
}
