package com.nva.RealTimeMessenger_v22.controller;

import com.google.gson.Gson;
import com.nva.RealTimeMessenger_v22.config.MapRuntime;
import com.nva.RealTimeMessenger_v22.entity.Message;
import com.nva.RealTimeMessenger_v22.repository.MessageRepository;
import com.nva.RealTimeMessenger_v22.repository.UserRepository;
import com.nva.RealTimeMessenger_v22.repository.UserRoomRepository;
import com.nva.RealTimeMessenger_v22.service.ChatService;
import com.nva.RealTimeMessenger_v22.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatService chatService;


    @Autowired
    private MapRuntime mapRuntime;

    @Autowired
    private Gson gson;


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

    @GetMapping("/getMessage")
    private ResponseEntity<?> getMessage(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "roomId", required = true) int roomId
    ) {
        return chatService.getMessageFormRoom(token, roomId);
    }

    @GetMapping("/getMessageByPage")
    private ResponseEntity<?> getMessageByPage(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "roomId", required = true) int roomId,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "date", required = true) String date
    ) {
        return chatService.getMessageFormRoomWithPaging(token, roomId, page, date);
    }

    @MessageMapping("/chat/{toRoom}")
    public void sendMessage(@DestinationVariable int toRoom, String message) {
        if (message != null) {
            System.out.println("handling send message: " + message + " to: " + toRoom);
            Message messageFromUser = gson.fromJson(message, Message.class);
            List<Integer> userRooms = userRoomRepository.getUserRoomByRoomId(toRoom, messageFromUser.getUserId());
            System.out.println(message);
            chatService.saveMessage(messageFromUser);
            for (Integer i : userRooms) {
                simpMessagingTemplate.convertAndSend("/topic/messages/" + i, message);
            }
            chatService.resetChatCache(toRoom);
        }
    }
}
