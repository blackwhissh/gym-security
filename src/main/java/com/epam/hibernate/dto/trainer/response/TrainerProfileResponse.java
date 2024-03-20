package com.epam.hibernate.dto.trainer.response;

import com.epam.hibernate.dto.trainee.TraineeListInfo;
import com.epam.hibernate.entity.TrainingType;

import java.util.Set;

public class TrainerProfileResponse {
    private String firstName;
    private String lastName;
    private TrainingType specialization;
    private Boolean isActive;
    private Set<TraineeListInfo> traineeList;

    public TrainerProfileResponse(String firstName, String lastName, TrainingType specialization,
                                  Boolean isActive, Set<TraineeListInfo> traineeList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.traineeList = traineeList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<TraineeListInfo> getTraineeList() {
        return traineeList;
    }

    public void setTraineeList(Set<TraineeListInfo> traineeList) {
        this.traineeList = traineeList;
    }
}
