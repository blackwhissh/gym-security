package com.epam.hibernate.dto.user;

public class UserInfoDTO {
    private String username;
    private String oldPassword;
    private String newPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }


    public String getNewPassword() {
        return newPassword;
    }

}
