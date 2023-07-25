package com.example.Task06_RealTimeChat.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    @Column(name = "message")
    private String message;
    @Column(name = "created_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;
    @Column(name = "room_id")
    private int roomId;
    @Column(name = "user_id")
    private int userId;

    public Message(int messageId, String message, LocalDateTime createdDate, int roomId, int userId) {
        this.messageId = messageId;
        this.message = message;
        this.createdDate = createdDate;
        this.roomId = roomId;
        this.userId = userId;
    }

    public Message() {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
