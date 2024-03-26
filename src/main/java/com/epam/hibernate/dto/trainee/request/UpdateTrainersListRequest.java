package com.epam.hibernate.dto.trainee.request;

import java.util.Set;

public class UpdateTrainersListRequest {
    private Set<String> trainers;

    public UpdateTrainersListRequest(Set<String> trainers) {
        this.trainers = trainers;
    }
    public Set<String> getTrainers() {
        return trainers;
    }

    public void setTrainers(Set<String> trainers) {
        this.trainers = trainers;
    }
}
