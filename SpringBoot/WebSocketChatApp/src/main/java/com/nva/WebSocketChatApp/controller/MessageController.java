package com.nva.WebSocketChatApp.controller;

import com.nva.WebSocketChatApp.model.Message;
import com.nva.WebSocketChatApp.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        System.out.println("Handling send message: " + message + "to " + to);

        boolean isExits = UserStorage.getInstance().getUsers().contains(to);
        if (isExits) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }
    }
}
