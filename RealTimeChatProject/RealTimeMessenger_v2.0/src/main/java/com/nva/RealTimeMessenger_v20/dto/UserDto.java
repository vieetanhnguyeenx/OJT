package com.nva.RealTimeMessenger_v20.dto;

public class UserDto {
    private int userId;
    private String username;
    private String avatar;
    private String identifyCode;

    public UserDto() {
    }

    public UserDto(int userId, String username, String avatar, String identifyCode) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.identifyCode = identifyCode;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", identifyCode='" + identifyCode + '\'' +
                '}';
    }
}
