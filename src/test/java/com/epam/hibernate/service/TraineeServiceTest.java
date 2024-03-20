package com.epam.hibernate.service;

import com.epam.hibernate.dto.trainee.request.TraineeRegisterRequest;
import com.epam.hibernate.dto.trainee.request.TraineeTrainingsRequest;
import com.epam.hibernate.dto.trainee.request.UpdateTraineeRequest;
import com.epam.hibernate.dto.trainee.request.UpdateTrainersListRequest;
import com.epam.hibernate.dto.trainee.response.*;
import com.epam.hibernate.dto.trainer.TrainerListInfo;
import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.*;
import com.epam.hibernate.repository.TraineeRepository;
import com.epam.hibernate.repository.TrainerRepository;
import com.epam.hibernate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TraineeServiceTest {
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private TrainerRepository trainerRepository;
    @InjectMocks
    private TraineeService traineeService;

    private Trainee createMockTrainee() {
        Trainee mockTrainee = mock(Trainee.class);
        User mockUser = mock(User.class);

        when(mockTrainee.getUser()).thenReturn(mockUser);

        return mockTrainee;
    }
    private Trainer createMockTrainer(){
        Trainer mockTrainer = mock(Trainer.class);
        User mockUser = mock(User.class);

        when(mockUser.getActive()).thenReturn(true);
        when(mockTrainer.getUser()).thenReturn(mockUser);

        return mockTrainer;
    }

    private List<Trainer> createMockTrainerList() {
        Trainer trainer = mock(Trainer.class);

        when(trainer.getUser()).thenReturn(new User("John","Doe",true,RoleEnum.TRAINER));
        return List.of(trainer);
    }

    private List<Training> createMockTrainingList() {
        Training training = mock(Training.class);

        when(training.getTrainingDate()).thenReturn(Date.valueOf("2024-10-10"));
        when(training.getTrainingName()).thenReturn("test");
        when(training.getTrainingDuration()).thenReturn(10);
        when(training.getTrainingType()).thenReturn(new TrainingType());
        when(training.getTrainer()).thenReturn(new Trainer(new TrainingType(TrainingTypeEnum.AGILITY), new User("John", "Doe", true, RoleEnum.TRAINEE)));

        return List.of(training);
    }


    @Test
    void createTraineeProfileOk() {
        when(userRepository.usernameExists(any())).thenReturn(false);

        when(traineeRepository.save(any(Trainee.class))).thenReturn(new Trainee());

        TraineeRegisterRequest validRequest = new TraineeRegisterRequest("John", "Doe",
                Date.valueOf("2001-10-10"), "123 Main St");

        ResponseEntity<TraineeRegisterResponse> responseEntity = traineeService.createProfile(validRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        TraineeRegisterResponse responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getUsername());
        assertNotNull(responseBody.getPassword());
    }

    @Test
    void createTraineeProfileSameNameOk() {
        when(userRepository.usernameExists(any())).thenReturn(true);

        when(traineeRepository.save(any(Trainee.class))).thenReturn(new Trainee());

        TraineeRegisterRequest validRequest = new TraineeRegisterRequest("John", "Doe",
                Date.valueOf("2001-10-10"), "123 Main St");

        ResponseEntity<TraineeRegisterResponse> responseEntity = traineeService.createProfile(validRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        TraineeRegisterResponse responseBody = responseEntity.getBody();

        assertNotNull(responseBody);
        assertNotNull(responseBody.getUsername());
        assertNotNull(responseBody.getPassword());

        assertNotNull(responseBody);
        assertNotNull(responseBody.getUsername());
        assertNotNull(responseBody.getPassword());

        assertEquals("John.Doe0", responseBody.getUsername());
    }

    @Test
    void selectTraineeProfileOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainee mockTrainee = createMockTrainee();
        when(traineeRepository.selectByUsername(any(String.class))).thenReturn(mockTrainee);

        LoginDTO loginDTO = new LoginDTO("admin", "admin");

        ResponseEntity<TraineeProfileResponse> response = traineeService.selectCurrentTraineeProfile(
                "John.Doe", loginDTO
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateTraineeProfileOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainee mockTrainee = createMockTrainee();
        when(traineeRepository.updateTrainee(any(String.class), any(), any(), any(), any(), any()))
                .thenReturn(mockTrainee);

        ResponseEntity<UpdateTraineeResponse> responseEntity = traineeService.updateTrainee(
                "John.Doe", new UpdateTraineeRequest(
                        new LoginDTO("admin", "admin"), "James", "Smith",
                        null, null, true
                ));

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    void deleteTraineeOk() throws AuthenticationException, AccessDeniedException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        when(userRepository.findByUsername(any())).thenReturn(new User(RoleEnum.ADMIN));

        traineeService.deleteTrainee("John.Doe", new LoginDTO("admin", "admin"));

        verify(userService, times(1)).authenticate(any(LoginDTO.class));
        verify(traineeRepository, times(1)).deleteTrainee("John.Doe");
    }

    @Test
    void getTrainingListOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        List<Training> mockTrainingList = createMockTrainingList();
        when(traineeRepository.getTrainingList(anyString(), any(), any(), any(), any()))
                .thenReturn(mockTrainingList);

        TraineeTrainingsRequest request = new TraineeTrainingsRequest(
                new LoginDTO("admin", "admin"), null, null, null, null
        );
        ResponseEntity<List<TraineeTrainingsResponse>> responseEntity = traineeService.getTrainingList("John.Doe", request);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    void notAssignedTrainersListOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainee mockTrainee = createMockTrainee();
        when(traineeRepository.selectByUsername(any(String.class))).thenReturn(mockTrainee);

        List<Trainer> mockTrainers = createMockTrainerList();
        when(trainerRepository.getAllTrainers()).thenReturn(mockTrainers);

        ResponseEntity<List<NotAssignedTrainer>> responseEntity = traineeService.notAssignedTrainersList("John.Doe", new LoginDTO("admin", "admin"));

        assertEquals(200, responseEntity.getStatusCode().value());
    }
    @Test
    void assignedTrainersListOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainee mockTrainee = createMockTrainee();
        when(traineeRepository.selectByUsername(any(String.class))).thenReturn(mockTrainee);

        List<Trainer> mockTrainers = createMockTrainerList();
        when(trainerRepository.getAllTrainers()).thenReturn(mockTrainers);

        User currentUser = new User("admin", "admin", true, RoleEnum.ADMIN);
        List<Trainer> assignedTrainers = traineeService.assignedTrainersList(currentUser, "John.Doe");

        assertEquals(0, assignedTrainers.size());
    }
    @Test
    void updateTrainersListOk() throws AuthenticationException {
        when(userService.authenticate(any(LoginDTO.class))).thenReturn(null);

        Trainee mockTrainee = createMockTrainee();
        when(traineeRepository.selectByUsername(any(String.class))).thenReturn(mockTrainee);

        Trainer mockTrainer = createMockTrainer();
        when(trainerRepository.selectByUsername(any(String.class))).thenReturn(mockTrainer);

        when(traineeRepository.save(any(Trainee.class))).thenReturn(new Trainee());

        Set<String> trainersSet = new HashSet<>();
        trainersSet.add("trainerUsername");
        UpdateTrainersListRequest request = new UpdateTrainersListRequest(
                new LoginDTO("John.Doe","password"),
                trainersSet
        );

        request.setTrainers(trainersSet);

        ResponseEntity<List<TrainerListInfo>> responseEntity = traineeService.updateTrainersList("John.Doe", request);

        assertEquals(200, responseEntity.getStatusCode().value());
        verify(trainerRepository, times(1)).selectByUsername("trainerUsername");
    }
}