package com.epam.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "training")
public class Training {
    @Id
    @SequenceGenerator(name = "training_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "training_id_seq", strategy = GenerationType.SEQUENCE)
    private Long trainingId;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @Column(name = "training_name", nullable = false)
    private String trainingName;
    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;
    @Column(name = "training_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date trainingDate;
    @Column(name = "training_duration", nullable = false)
    private Integer trainingDuration;

    public Training(Trainer trainer, Trainee trainee, String trainingName, TrainingType trainingType, Date trainingDate, Integer trainingDuration) {
        this.trainer = trainer;
        this.trainee = trainee;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    public Training() {
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Integer getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Integer trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainingId=" + trainingId +
                ", trainer=" + trainer.getTrainerId() +
                ", trainee=" + trainee.getTraineeId() +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType.getTrainingTypeName() +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}
