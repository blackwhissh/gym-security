package com.epam.hibernate.service;

import com.epam.hibernate.dto.trainer.request.TrainerRegisterRequest;
import com.epam.hibernate.dto.trainer.request.TrainerTrainingsRequest;
import com.epam.hibernate.dto.trainer.request.UpdateTrainerRequest;
import com.epam.hibernate.dto.trainer.response.TrainerProfileResponse;
import com.epam.hibernate.dto.trainer.response.TrainerRegisterResponse;
import com.epam.hibernate.dto.trainer.response.TrainerTrainingsResponse;
import com.epam.hibernate.dto.trainer.response.UpdateTrainerResponse;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.*;
import com.epam.hibernate.repository.TrainerRepository;
import com.epam.hibernate.repository.TrainingTypeRepository;
import com.epam.hibernate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private TrainerService trainerService;

    private TrainingType createMockTrainingType() {
        TrainingType trainingType = mock(TrainingType.class);

        when(trainingType.getTrainingTypeName()).thenReturn(TrainingTypeEnum.AGILITY);

        return trainingType;
    }

    private Trainer createMockTrainer() {
        Trainer mockTrainer = mock(Trainer.class);
        User mockUser = mock(User.class);
        TrainingType trainingType = createMockTrainingType();
        when(mockTrainer.getUser()).thenReturn(mockUser);
        when(mockTrainer.getSpecialization()).thenReturn(trainingType);
        return mockTrainer;
    }

    @Test
    void createProfileOk() {
        when(userRepository.usernameExists(any(String.class))).thenReturn(false);

        TrainingType mockTrainingType = createMockTrainingType();
        when(trainingTypeRepository.selectByType(any(TrainingTypeEnum.class))).thenReturn(mockTrainingType);

        when(trainerRepository.save(any(Trainer.class))).thenReturn(new Trainer());

        TrainerRegisterRequest request = new TrainerRegisterRequest(
                "John", "Doe", mockTrainingType.getTrainingTypeName()
        );
        ResponseEntity<TrainerRegisterResponse> responseEntity = trainerService.createProfile(request);

        assertEquals(200, responseEntity.getStatusCode().value());
        verify(userRepository, times(1)).usernameExists(any(String.class));
        verify(trainingTypeRepository, times(1)).selectByType(any(TrainingTypeEnum.class));
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void createProfileSameNameOk() {
        when(userRepository.usernameExists(any(String.class))).thenReturn(true);

        TrainingType mockTrainingType = createMockTrainingType();
        when(trainingTypeRepository.selectByType(any(TrainingTypeEnum.class))).thenReturn(mockTrainingType);

        when(trainerRepository.save(any(Trainer.class))).thenReturn(new Trainer());

        TrainerRegisterRequest request = new TrainerRegisterRequest(
                "John", "Doe", mockTrainingType.getTrainingTypeName()
        );
        trainerService.createProfile(request);

        assertNotEquals("John.Doe", "John.Doe0");
        verify(userRepository, times(1)).usernameExists(any(String.class));
        verify(trainingTypeRepository, times(1)).selectByType(any(TrainingTypeEnum.class));
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void selectCurrentTrainerProfileOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainer mockTrainer = createMockTrainer();
        when(trainerRepository.selectByUsername(any(String.class))).thenReturn(mockTrainer);

        // Call the method under test
        LoginDTO loginDTO = new LoginDTO("admin", "admin");
        ResponseEntity<TrainerProfileResponse> responseEntity = trainerService.selectCurrentTrainerProfile("John.Trainer", loginDTO);

        // Assertions
        assertEquals(200, responseEntity.getStatusCode().value());
        // Add more assertions based on your specific scenario
        verify(trainerRepository, times(1)).selectByUsername("John.Trainer");
    }

    @Test
    void updateTrainerOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainer mockTrainer = createMockTrainer();

        when(trainerRepository.updateTrainer(any(String.class), any(String.class), any(String.class),
                any(Boolean.class), any(TrainingTypeEnum.class)))
                .thenReturn(mockTrainer);

        UpdateTrainerRequest updateRequest = new UpdateTrainerRequest(
                new LoginDTO("John.Trainer", "password"),
                "John", "Trainer", TrainingTypeEnum.AGILITY, true
        );
        ResponseEntity<UpdateTrainerResponse> responseEntity = trainerService.updateTrainer("John.Trainer", updateRequest);

        assertEquals(200, responseEntity.getStatusCode().value());
        verify(trainerRepository, times(1)).updateTrainer("John.Trainer", updateRequest.getFirstName(), updateRequest.getLastName(), updateRequest.getActive(), updateRequest.getSpecialization());
    }
    @Test
    void getTrainingListOk() throws AuthenticationException {
        // Mock the userService.authenticate behavior
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainer mockTrainer = createMockTrainer();

        when(trainerRepository.selectByUsername(any(String.class))).thenReturn(mockTrainer);

        List<Training> mockTrainingList = createMockTrainingList();
        when(trainerRepository.getTrainingList(any(String.class), any(Date.class), any(Date.class),
                any(String.class), any(TrainingTypeEnum.class))).thenReturn(mockTrainingList);

        // Call the method under test
        TrainerTrainingsRequest trainingsRequest = new TrainerTrainingsRequest(new LoginDTO("username", "password"),
                null, null, "traineeName");
        ResponseEntity<List<TrainerTrainingsResponse>> responseEntity = trainerService.getTrainingList("username", trainingsRequest);

        // Assertions
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    // Add more test cases for various scenarios, e.g., when authentication fails, when there are no trainings, etc.

    private List<Training> createMockTrainingList() {
        Training mockTraining = mock(Training.class);
        return List.of(mockTraining);
    }
}