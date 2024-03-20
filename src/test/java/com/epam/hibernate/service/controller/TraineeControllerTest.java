package com.epam.hibernate.service.controller;

import com.epam.hibernate.controller.TraineeController;
import com.epam.hibernate.dto.trainee.request.TraineeRegisterRequest;
import com.epam.hibernate.dto.trainee.response.TraineeRegisterResponse;
import com.epam.hibernate.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TraineeControllerTest {
    @InjectMocks
    private TraineeController traineeController;

    @Mock
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerTrainee_ValidRequest_ReturnsOkResponse() {
        when(traineeService.createProfile(any(TraineeRegisterRequest.class))).thenReturn(ResponseEntity.ok().build());

        TraineeRegisterRequest validRequest = new TraineeRegisterRequest("John","Doe",null,null);

        ResponseEntity<TraineeRegisterResponse> responseEntity = traineeController.registerTrainee(validRequest);

        assertEquals(200, responseEntity.getStatusCode().value());
    }
}
