package com.epam.hibernate.dto.trainee.request;

import com.epam.hibernate.entity.TrainingTypeEnum;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class TraineeTrainingsRequest {
    private Date from;
    private Date to;
    private String trainerName;
    private TrainingTypeEnum trainingType;

    public TraineeTrainingsRequest(@Nullable Date from, @Nullable Date to,
                                   @Nullable String trainerName, @Nullable TrainingTypeEnum trainingType) {
        this.from = from;
        this.to = to;
        this.trainerName = trainerName;
        this.trainingType = trainingType;
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
