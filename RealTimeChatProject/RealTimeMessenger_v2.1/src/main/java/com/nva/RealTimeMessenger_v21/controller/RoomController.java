package com.nva.RealTimeMessenger_v21.controller;

import com.nva.RealTimeMessenger_v21.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/getRoomInfo")
    private ResponseEntity<?> getRoomInformation(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "roomId", required = true) int roomId
    ) {
        return roomService.getRoomInformation(token, roomId);
    }

    @GetMapping("/getUserRoomInfo")
    private ResponseEntity<?> getUserRoomInfo(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "roomId", required = true) int roomId
    ) {
        return roomService.getUserRoomInfo(token, roomId);
    }
}
