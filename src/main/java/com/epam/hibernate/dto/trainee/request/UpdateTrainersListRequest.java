package com.epam.hibernate.dto.trainee.request;

import com.epam.hibernate.dto.user.LoginDTO;

import java.util.Set;

public class UpdateTrainersListRequest {
    private LoginDTO loginDTO;
    private Set<String> trainers;

    public UpdateTrainersListRequest(LoginDTO loginDTO, Set<String> trainers) {
        this.loginDTO = loginDTO;
        this.trainers = trainers;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public Set<String> getTrainers() {
        return trainers;
    }

    public void setTrainers(Set<String> trainers) {
        this.trainers = trainers;
    }
}
