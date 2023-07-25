package com.nva.RealTimeMessenger_v21.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int room_id;
    @Column(name = "room_name", columnDefinition = "nvarchar(255)")
    private String name;
    @Column(name = "room_type", columnDefinition = "bit")
    private Boolean roomType;
    @Column(name = "latest_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS", timezone = "Asia/Ho_Chi_Minh")
    //@Temporal(TemporalType.TIMESTAMP)
    private Date latestUpdateDate;
    @Column(name = "user_1", columnDefinition = "int")
    private Integer user1;
    @Column(name = "user_2", columnDefinition = "int")
    private Integer user2;
    @Column(name = "room_image", columnDefinition = "nvarchar(MAX)")
    private String roomImage;

    public Room() {
    }

    public Room(int room_id, String name, Boolean roomType, Date latestUpdateDate, Integer user1, Integer user2, String roomImage) {
        this.room_id = room_id;
        this.name = name;
        this.roomType = roomType;
        this.latestUpdateDate = latestUpdateDate;
        this.user1 = user1;
        this.user2 = user2;
        this.roomImage = roomImage;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRoomType() {
        return roomType;
    }

    public void setRoomType(Boolean roomType) {
        this.roomType = roomType;
    }

    public Date getLatestUpdateDate() {
        return latestUpdateDate;
    }

    public void setLatestUpdateDate(Date latestUpdateDate) {
        this.latestUpdateDate = latestUpdateDate;
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

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id=" + room_id +
                ", name='" + name + '\'' +
                ", roomType=" + roomType +
                ", latestUpdateDate=" + latestUpdateDate +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", roomImage='" + roomImage + '\'' +
                '}';
    }
}
