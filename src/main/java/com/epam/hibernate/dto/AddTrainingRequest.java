package com.epam.hibernate.dto;

import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.TrainingTypeEnum;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class AddTrainingRequest {
    private LoginDTO loginDTO;
    private String traineeUsername;
    private String trainerUsername;
    private String trainingName;
    private Date trainingDate;
    private Integer duration;
    private TrainingTypeEnum trainingType;

    public AddTrainingRequest(@NotNull LoginDTO loginDTO,@NotNull String traineeUsername,
                              @NotNull String trainerUsername, @NotNull String trainingName,
                              @NotNull Date trainingDate,@NotNull Integer duration,
                              @NotNull TrainingTypeEnum trainingType) {
        this.loginDTO = loginDTO;
        this.traineeUsername = traineeUsername;
        this.trainerUsername = trainerUsername;
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.duration = duration;
        this.trainingType = trainingType;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public String getTraineeUsername() {
        return traineeUsername;
    }

    public void setTraineeUsername(String traineeUsername) {
        this.traineeUsername = traineeUsername;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public TrainingTypeEnum getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingTypeEnum trainingType) {
        this.trainingType = trainingType;
    }
}
