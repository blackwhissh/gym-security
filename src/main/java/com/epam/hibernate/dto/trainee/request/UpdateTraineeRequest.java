package com.epam.hibernate.dto.trainee.request;

import com.epam.hibernate.dto.user.LoginDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.util.Date;

public class UpdateTraineeRequest {
    private LoginDTO loginDTO;
    private String firstName;
    private String lastName;
    private Date dob;
    private String address;
    private Boolean isActive;

    public UpdateTraineeRequest(@NotNull LoginDTO loginDTO, @NotNull String firstName, @NotNull String lastName,
                                @Nullable Date dob, @Nullable String address, @NotNull Boolean isActive) {
        this.loginDTO = loginDTO;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
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

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }
}
