package com.epam.hibernate.service;

import com.epam.hibernate.dto.trainee.TraineeListInfo;
import com.epam.hibernate.dto.trainer.request.TrainerRegisterRequest;
import com.epam.hibernate.dto.trainer.request.TrainerTrainingsRequest;
import com.epam.hibernate.dto.trainer.request.UpdateTrainerRequest;
import com.epam.hibernate.dto.trainer.response.TrainerProfileResponse;
import com.epam.hibernate.dto.trainer.response.TrainerRegisterResponse;
import com.epam.hibernate.dto.trainer.response.TrainerTrainingsResponse;
import com.epam.hibernate.dto.trainer.response.UpdateTrainerResponse;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.RoleEnum;
import com.epam.hibernate.entity.Trainer;
import com.epam.hibernate.entity.Training;
import com.epam.hibernate.entity.User;
import com.epam.hibernate.repository.TrainerRepository;
import com.epam.hibernate.repository.TrainingTypeRepository;
import com.epam.hibernate.repository.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hibernate.Utils.generateUsername;

@Service
public class TrainerService {
    private final Timer registerTimer;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public TrainerService(MeterRegistry meterRegistry, TrainingTypeRepository trainingTypeRepository,
                          TrainerRepository trainerRepository,
                          UserRepository userRepository, UserService userService) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.registerTimer = Timer.builder("trainer_register_timer")
                .description("Times Registering Trainer")
                .register(meterRegistry);
    }

    public ResponseEntity<TrainerRegisterResponse> createProfile(@NotNull TrainerRegisterRequest request) {
        User trainerUser = new User(request.getFirstName(), request.getLastName(), true, RoleEnum.TRAINER);
        registerTimer.record(() -> {
            if (!userRepository.usernameExists(generateUsername(trainerUser.getFirstName(), trainerUser.getLastName(), false))) {
                trainerUser.setUsername(generateUsername(trainerUser.getFirstName(), trainerUser.getLastName(), false));
            } else {
                trainerUser.setUsername(generateUsername(trainerUser.getFirstName(), trainerUser.getLastName(), true));
            }
            Trainer trainer = new Trainer(trainingTypeRepository.selectByType(request.getSpecialization()),
                    trainerUser);
            trainerRepository.save(trainer);
        });
        return ResponseEntity.ok().body(new TrainerRegisterResponse(
                trainerUser.getUsername(),
                trainerUser.getPassword()
        ));
    }

    public ResponseEntity<TrainerProfileResponse> selectCurrentTrainerProfile(@NotNull String username, @NotNull LoginDTO loginDTO) throws AuthenticationException {
        userService.authenticate(loginDTO);
        Trainer trainer = trainerRepository.selectByUsername(username);
        return ResponseEntity.ok(new TrainerProfileResponse(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getSpecialization(),
                trainer.getUser().getActive(),
                trainer.getTrainees().stream().map(trainee -> new TraineeListInfo(
                        trainee.getUser().getUsername(),
                        trainee.getUser().getFirstName(),
                        trainee.getUser().getLastName()
                )).collect(Collectors.toSet())
        ));
    }

//    public Trainer selectProfile(@NotNull String newUsername, @NotNull User admin) throws AuthenticationException, AccessDeniedException {
//        userRepository.authenticate(admin.getUsername(), admin.getPassword());
//        return trainerRepository.selectByUsername(newUsername);
//    }

    public ResponseEntity<UpdateTrainerResponse> updateTrainer(@NotNull String username, @NotNull UpdateTrainerRequest request) throws AuthenticationException {
        userService.authenticate(request.getLoginDTO());

        Trainer trainer = trainerRepository.updateTrainer(username, request.getFirstName(), request.getLastName(),
                request.getActive(), request.getSpecialization());
        return ResponseEntity.ok().body(new UpdateTrainerResponse(
                trainer.getUser().getUsername(),
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getSpecialization(),
                trainer.getUser().getActive(),
                trainer.getTrainees().stream().map(trainee -> new TraineeListInfo(
                        trainee.getUser().getUsername(),
                        trainee.getUser().getFirstName(),
                        trainee.getUser().getLastName()
                )).collect(Collectors.toSet())
        ));
    }

    @Transactional
    public ResponseEntity<List<TrainerTrainingsResponse>> getTrainingList(@NotNull String username, @NotNull TrainerTrainingsRequest request) throws AuthenticationException {
        userService.authenticate(request.getLoginDTO());

        List<Training> trainingList = trainerRepository.getTrainingList(username,
                request.getFrom(), request.getTo(), request.getTraineeName(),
                trainerRepository.selectByUsername(username).getSpecialization().getTrainingTypeName());
        return ResponseEntity.ok(trainingList.stream().map(training -> new TrainerTrainingsResponse(
                training.getTrainingName(),
                training.getTrainingDate(),
                training.getTrainingType(),
                training.getTrainingDuration(),
                training.getTrainer().getUser().getFirstName()
        )).toList());
    }


}
