package com.epam.hibernate.dto.trainee.response;

import com.epam.hibernate.entity.TrainingType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TraineeTrainingsResponse {
    private String trainingName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date trainingDate;
    private TrainingType trainingType;
    private Integer duration;
    private String trainerName;

    public TraineeTrainingsResponse(String trainingName, Date trainingDate, TrainingType trainingType, Integer duration, String trainerName) {
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingType = trainingType;
        this.duration = duration;
        this.trainerName = trainerName;
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

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
}
