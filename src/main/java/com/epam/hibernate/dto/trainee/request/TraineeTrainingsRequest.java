package com.epam.hibernate.dto.trainee.request;

import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.TrainingTypeEnum;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class TraineeTrainingsRequest {
    private LoginDTO loginDTO;
    private Date from;
    private Date to;
    private String trainerName;
    private TrainingTypeEnum trainingType;

    public TraineeTrainingsRequest(@NotNull LoginDTO loginDTO, @Nullable Date from, @Nullable Date to,
                                   @Nullable String trainerName, @Nullable TrainingTypeEnum trainingType) {
        this.loginDTO = loginDTO;
        this.from = from;
        this.to = to;
        this.trainerName = trainerName;
        this.trainingType = trainingType;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public TrainingTypeEnum getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingTypeEnum trainingType) {
        this.trainingType = trainingType;
    }
}
