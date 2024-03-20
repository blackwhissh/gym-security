package com.epam.hibernate.service;

import com.epam.hibernate.dto.AddTrainingRequest;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.*;
import com.epam.hibernate.repository.TraineeRepository;
import com.epam.hibernate.repository.TrainerRepository;
import com.epam.hibernate.repository.TrainingTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.io.NotActiveException;
import java.nio.file.AccessDeniedException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private TrainingService trainingService;

    private Trainee createMockTrainee() {
        Trainee mockTrainee = mock(Trainee.class);
        User mockUser = mock(User.class);

        when(mockTrainee.getUser()).thenReturn(mockUser);
        when(mockUser.getActive()).thenReturn(true);
        return mockTrainee;
    }

    private Trainer createMockTrainer() {
        Trainer mockTrainer = mock(Trainer.class);
        User mockUser = mock(User.class);

        when(mockTrainer.getUser()).thenReturn(mockUser);
        when(mockUser.getActive()).thenReturn(true);

        return mockTrainer;
    }

    @Test
    void addTrainingOk() throws AuthenticationException, NotActiveException, AccessDeniedException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainee mockTrainee = createMockTrainee();
        Trainer mockTrainer = createMockTrainer();
        when(traineeRepository.selectByUsername(any(String.class))).thenReturn(mockTrainee);
        when(trainerRepository.selectByUsername(any(String.class))).thenReturn(mockTrainer);

        TrainingType mockTrainingType = createMockTrainingType();
        when(trainingTypeRepository.selectByType(any(TrainingTypeEnum.class))).thenReturn(mockTrainingType);

        when(mockTrainer.getSpecialization()).thenReturn(mockTrainingType);

        when(trainerRepository.save(any(Trainer.class))).thenReturn(null);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(null);

        AddTrainingRequest addTrainingRequest = new AddTrainingRequest(new LoginDTO("username", "password"),
                "traineeUsername", "trainerUsername", "test", Date.valueOf("2020-10-10"),
                50, TrainingTypeEnum.AGILITY);
        ResponseEntity<?> responseEntity = trainingService.addTraining(addTrainingRequest);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals("Training added successfully", responseEntity.getBody());

    }

    private TrainingType createMockTrainingType() {
        TrainingType mockTrainingType = mock(TrainingType.class);

        when(mockTrainingType.getTrainingTypeName()).thenReturn(TrainingTypeEnum.AGILITY);

        return mockTrainingType;
    }


}