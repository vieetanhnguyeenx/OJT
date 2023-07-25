package com.example.Task06_RealTimeChat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "muser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name = "name", columnDefinition = "nvarchar(255)")
    private String username;
    @Column(name = "password", columnDefinition = "nvarchar(255)")
    private String password;
    @Column(name = "identify_code", columnDefinition = "nvarchar(255)")
    private String identifyCode;
    @Column(name = "avatar", columnDefinition = "nvarchar(255)")
    private String avatar;

    public User() {
    }

    public User(int userId, String username, String password, String identifyCode, String avatar) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identifyCode='" + identifyCode + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
