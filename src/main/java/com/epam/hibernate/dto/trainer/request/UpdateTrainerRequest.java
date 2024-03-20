package com.epam.hibernate.dto.trainer.request;

import com.epam.hibernate.dto.user.LoginDTO;
import com.epam.hibernate.entity.TrainingTypeEnum;
import org.jetbrains.annotations.NotNull;

public class UpdateTrainerRequest {
    private LoginDTO loginDTO;
    private String firstName;
    private String lastName;
    private TrainingTypeEnum specialization;
    private Boolean isActive;

    public UpdateTrainerRequest(@NotNull LoginDTO loginDTO, @NotNull String firstName, @NotNull String lastName,
                                TrainingTypeEnum specialization, @NotNull Boolean isActive) {
        this.loginDTO = loginDTO;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
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
