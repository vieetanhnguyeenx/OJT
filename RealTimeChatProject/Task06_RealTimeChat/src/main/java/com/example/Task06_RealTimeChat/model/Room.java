package com.example.Task06_RealTimeChat.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    @Column(name = "room_name", columnDefinition = "nvarchar(MAX)")
    private String roomName;
    @Column(name = "room_type", columnDefinition = "bit")
    private boolean roomType;
    @Column(name = "latest_update", columnDefinition = "TIMESTAMP")
    private LocalDateTime latestUpdate;
    @Column(name = "room_image", columnDefinition = "nvarchar(255)")
    private String roomImage;
    @Column(name = "user_1", columnDefinition = "int")
    private Integer user1;

    @Column(name = "user_2", columnDefinition = "int")
    private Integer user2;

    public Room() {
    }

    public Room(int roomId, String roomName, boolean roomType, LocalDateTime latestUpdate, String roomImage, Integer user1, Integer user2) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.latestUpdate = latestUpdate;
        this.roomImage = roomImage;
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isRoomType() {
        return roomType;
    }

    public void setRoomType(boolean roomType) {
        this.roomType = roomType;
    }

    public LocalDateTime getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(LocalDateTime latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    public Integer getUser1() {
        return user1;
    }

    public void setUser1(Integer user1) {
        this.user1 = user1;
    }

    public Integer getUser2() {
        return user2;
    }

    public void setUser2(Integer user2) {
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomType=" + roomType +
                ", latestUpdate=" + latestUpdate +
                ", roomImage='" + roomImage + '\'' +
                ", user1=" + user1 +
                ", user2=" + user2 +
                '}';
    }
}
