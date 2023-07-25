package com.example.Task06_RealTimeChat.dto;

import com.example.Task06_RealTimeChat.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;

public class RoomUserDto {
    private int roomId;
    private String roomName;
    private boolean roomType;
    private LocalDateTime latestUpdate;
    private Integer user1;
    private Integer user2;
}
