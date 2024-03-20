package com.epam.hibernate.dto.trainer.request;

import com.epam.hibernate.entity.TrainingTypeEnum;
import org.jetbrains.annotations.NotNull;

public class TrainerRegisterRequest {
    private String firstName;
    private String lastName;
    private TrainingTypeEnum specialization;

    public TrainerRegisterRequest(@NotNull String firstName, @NotNull String lastName, @NotNull TrainingTypeEnum specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
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

    public TrainingTypeEnum getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingTypeEnum specialization) {
        this.specialization = specialization;
    }
}
