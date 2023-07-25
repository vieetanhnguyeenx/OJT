package com.example.Task06_RealTimeChat.service;

import com.example.Task06_RealTimeChat.dto.LoginUserDto;
import com.example.Task06_RealTimeChat.model.Message;
import com.example.Task06_RealTimeChat.model.Room;
import com.example.Task06_RealTimeChat.model.User;
import com.example.Task06_RealTimeChat.repository.MessageRepository;
import com.example.Task06_RealTimeChat.repository.RoomRepository;
import com.example.Task06_RealTimeChat.repository.UserRepository;
import com.example.Task06_RealTimeChat.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService implements IService {


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User authentication(String token) {
        LoginUserDto loginUserDto = TokenHelper.gson.fromJson(new String(TokenHelper.decoder.decode(token)), LoginUserDto.class);
        return userRepository.findByUserNameAndUserId(loginUserDto.getUsername(), loginUserDto.getUserId());
    }

    public ResponseEntity<List<Room>> fetchRoom(String token) {
        try {
            User user = authentication(token);
            if (user != null) {
                List<Room> rooms = roomRepository.findByUserId(user.getUserId());
                return ResponseEntity.ok().body(rooms);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    public ResponseEntity<List<Message>> getMessage(String token, int roomId) {
        try {
            User user = authentication(token);
            if (user != null) {
                return ResponseEntity.ok().body(messageRepository.findByRoomId(roomId));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
