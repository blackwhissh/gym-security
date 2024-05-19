package com.epam.hibernate.profiles;

import com.epam.hibernate.dto.trainee.request.TraineeRegisterRequest;
import com.epam.hibernate.dto.trainer.request.TrainerRegisterRequest;
import com.epam.hibernate.entity.*;
import com.epam.hibernate.service.TraineeService;
import com.epam.hibernate.service.TrainerService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev","admin"})
public class DevDataLoader {
    private final TraineeService traineeService;
    private final TrainerService trainerService;


    public DevDataLoader(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @PostConstruct
    @Transactional
    public void loadSampleData(){

        traineeService.createProfile(new TraineeRegisterRequest(
                "trainee","trainee",null,null
        ));

        trainerService.createProfile(new TrainerRegisterRequest(
                "trainer","trainer","AGILITY"
        ));
    }
}
