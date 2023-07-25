package com.example.Task06_RealTimeChat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UserRoom")
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "IsAdmin", columnDefinition = "bit")
    private Boolean isAdmin;
    @Column(name = "UserId", columnDefinition = "int")
    private String userId;
    @Column(name = "RoomId", columnDefinition = "int")
    private String roomId;

    public UserRoom() {
    }

    public UserRoom(int id, Boolean isAdmin, String userId, String roomId) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.userId = userId;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "UserRoom{" +
                "id=" + id +
                ", isAdmin=" + isAdmin +
                ", userId='" + userId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
