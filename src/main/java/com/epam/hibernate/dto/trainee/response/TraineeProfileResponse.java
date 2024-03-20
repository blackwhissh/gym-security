package com.epam.hibernate.dto.trainee.response;

import com.epam.hibernate.dto.trainer.TrainerListInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;

public class TraineeProfileResponse {
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
    private String address;
    private Boolean isActive;
    private Set<TrainerListInfo> trainerList;

    public TraineeProfileResponse(String firstName, String lastName, Date dob, String address,
                                  Boolean isActive, Set<TrainerListInfo> trainerList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.isActive = isActive;
        this.trainerList = trainerList;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<TrainerListInfo> getTrainerList() {
        return trainerList;
    }

    public void setTrainerList(Set<TrainerListInfo> trainerList) {
        this.trainerList = trainerList;
    }
}
