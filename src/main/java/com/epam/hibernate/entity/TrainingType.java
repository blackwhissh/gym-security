package com.epam.hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "training_type")
public class TrainingType {
    @Id
    @SequenceGenerator(name = "training_type_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "training_type_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "training_type_id", nullable = false)
    private Integer trainingTypeId;
    @Column(name = "training_type_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainingTypeEnum trainingTypeName;

    public TrainingType() {
    }

    public TrainingType(TrainingTypeEnum trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    public Integer getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(Integer trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public TrainingTypeEnum getTrainingTypeName() {
        return trainingTypeName;
    }

    public void setTrainingTypeName(TrainingTypeEnum trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "trainingTypeId=" + trainingTypeId +
                ", trainingTypeName=" + trainingTypeName +
                '}';
    }
}
