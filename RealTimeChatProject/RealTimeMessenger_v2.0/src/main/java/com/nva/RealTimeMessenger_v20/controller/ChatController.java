package com.nva.RealTimeMessenger_v20.controller;

import com.nva.RealTimeMessenger_v20.model.Room;
import com.nva.RealTimeMessenger_v20.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/chat-online")
    private ModelAndView getChat(
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ChatBox.html");
        return modelAndView;
    }

    @GetMapping("/fetchAllRoom")
    private ResponseEntity<?> fetchAllRoom(
            @RequestParam(value = "token", required = true) String token
    ) {
        return roomService.fetchRoom(token);
    }
}
