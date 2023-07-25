package com.example.Task06_RealTimeChat.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class LoginUserDto {
    private int userId;
    private String username;
    private String identifyCode;
    private String avatar;

    public LoginUserDto() {
    }

    public LoginUserDto(int userId, String username, String identifyCode, String avatar) {
        this.userId = userId;
        this.username = username;
        this.identifyCode = identifyCode;
        this.avatar = avatar;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", identifyCode='" + identifyCode + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
