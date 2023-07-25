package com.nva.RealTimeMessenger_v21.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    @Column(name = "message", columnDefinition = "nvarchar(MAX)")
    private String message;
    @Column(name = "created_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate;
    @Column(name = "room_id", columnDefinition = "int")
    private Integer roomId;
    @Column(name = "user_id", columnDefinition = "int")
    private Integer userId;

    public Message() {
    }

    public Message(int messageId, String message, Date createdDate, Integer roomId, Integer userId) {
        this.messageId = messageId;
        this.message = message;
        this.createdDate = createdDate;
        this.roomId = roomId;
        this.userId = userId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", createdDate=" + createdDate +
                ", roomId=" + roomId +
                ", userId=" + userId +
                '}';
    }
}
