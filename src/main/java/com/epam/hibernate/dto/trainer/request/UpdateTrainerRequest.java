package com.epam.hibernate.dto.trainer.request;

import com.epam.hibernate.entity.TrainingTypeEnum;
import org.jetbrains.annotations.NotNull;

public class UpdateTrainerRequest {
    private String firstName;
    private String lastName;
    private TrainingTypeEnum specialization;
    private Boolean isActive;

    public UpdateTrainerRequest(@NotNull String firstName, @NotNull String lastName,
                                TrainingTypeEnum specialization, @NotNull Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
