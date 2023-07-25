package com.nva.RealTimeMessenger_v21.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.util.Date;

public class RoomDto {
    private int room_id;
    private String name;
    private Boolean roomType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private Date latestUpdateDate;
    private Integer user1;
    private Integer user2;
    private String roomImage;
}
