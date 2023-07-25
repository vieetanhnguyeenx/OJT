package com.nva.RealTimeMessenger_v20.model;


import jakarta.persistence.*;

@Entity
@Table(name = "c_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "username", columnDefinition = "nvarchar(255)")
    private String username;
    @Column(name = "password", columnDefinition = "nvarchar(255)")
    private String password;
    @Column(name = "avartar", columnDefinition = "nvarchar(MAX)")
    private String avatar;
    @Column(name = "identify_code", columnDefinition = "nvarchar(255)")
    private String identifyCode;

    public User() {
    }

    public User(int userId, String username, String password, String avatar, String identifyCode) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", identifyCode='" + identifyCode + '\'' +
                '}';
    }
}
