package com.epam.hibernate.dto.trainee.request;

import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public class TraineeRegisterRequest {
    private String firstName;
    private String lastName;
    private Date dob;
    private String address;

    public TraineeRegisterRequest(@NotNull String firstName, @NotNull String lastName, @Nullable Date dob, @Nullable String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
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
}
