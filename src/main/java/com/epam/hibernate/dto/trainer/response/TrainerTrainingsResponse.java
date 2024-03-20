package com.epam.hibernate.dto.trainer.response;

import com.epam.hibernate.entity.TrainingType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TrainerTrainingsResponse {
    private String trainingName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date trainingDate;
    private TrainingType trainingType;
    private Integer duration;
    private String traineeName;

    public TrainerTrainingsResponse(String trainingName, Date trainingDate,
                                    TrainingType trainingType, Integer duration, String traineeName) {
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingType = trainingType;
        this.duration = duration;
        this.traineeName = traineeName;
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

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }
}
