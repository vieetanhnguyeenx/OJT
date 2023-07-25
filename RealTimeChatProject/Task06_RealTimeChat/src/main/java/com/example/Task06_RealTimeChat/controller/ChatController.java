package com.example.Task06_RealTimeChat.controller;

import com.example.Task06_RealTimeChat.model.Message;
import com.example.Task06_RealTimeChat.model.Room;
import com.example.Task06_RealTimeChat.model.User;
import com.example.Task06_RealTimeChat.repository.MessageRepository;
import com.example.Task06_RealTimeChat.repository.RoomRepository;
import com.example.Task06_RealTimeChat.repository.UserRepository;
import com.example.Task06_RealTimeChat.repository.UserRoomRepository;
import com.example.Task06_RealTimeChat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @GetMapping("/chat-online")
    private ModelAndView getChat(
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("white_chat.html");
        return modelAndView;
    }

    @GetMapping("/fetchAllRoom")
    private ResponseEntity<List<Room>> fetchAllRoom(
            @RequestParam(value = "token", required = true) String token
    ) {
        return chatService.fetchRoom(token);
    }

    @GetMapping("/renderChat")
    private ResponseEntity<List<Message>> renderChat(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "roomId", required = true) int roomId
    ) {
        return chatService.getMessage(token, roomId);
    }

    @MessageMapping("/chat/{toRoom}")
    public void sendMessage(@DestinationVariable int toRoom, Message message) {
        if (message != null) {
            System.out.println("handling send message: " + message + " to: " + toRoom);
            List<Integer> userRooms = userRoomRepository.getAllUserIdInRoom(toRoom, message.getUserId());
            message.setCreatedDate(LocalDateTime.now());
            messageRepository.save(message);
            for (Integer i : userRooms) {
                simpMessagingTemplate.convertAndSend("/topic/messages/" + i, message);
            }
        }
    }
}
