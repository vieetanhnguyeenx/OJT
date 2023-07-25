package com.nva.RealTimeMessenger_v21.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_room")
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_admin", columnDefinition = "bit")
    private Boolean isAdmin;
    @Column(name = "user_id", columnDefinition = "int")
    private Integer userId;
    @Column(name = "room_id", columnDefinition = "int")
    private Integer roomId;
    @Column(name = "alias_name", columnDefinition = "nvarchar(255)")
    private String aliasName;

    public UserRoom() {
    }

    public UserRoom(int id, Boolean isAdmin, Integer userId, Integer roomId, String aliasName) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.userId = userId;
        this.roomId = roomId;
        this.aliasName = aliasName;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    @Override
    public String toString() {
        return "UserRoom{" +
                "id=" + id +
                ", isAdmin=" + isAdmin +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", aliasName='" + aliasName + '\'' +
                '}';
    }
}
