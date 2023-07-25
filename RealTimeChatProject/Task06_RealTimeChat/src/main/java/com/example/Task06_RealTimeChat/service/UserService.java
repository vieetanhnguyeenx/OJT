package com.example.Task06_RealTimeChat.service;

import com.example.Task06_RealTimeChat.dto.LoginUserDto;
import com.example.Task06_RealTimeChat.model.User;
import com.example.Task06_RealTimeChat.repository.MessageRepository;
import com.example.Task06_RealTimeChat.repository.RoomRepository;
import com.example.Task06_RealTimeChat.repository.UserRepository;
import com.example.Task06_RealTimeChat.util.TokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User authentication(String token) {
        LoginUserDto loginUserDto = TokenHelper.gson.fromJson(new String(TokenHelper.decoder.decode(token)), LoginUserDto.class);
        return userRepository.findByUserNameAndUserId(loginUserDto.getUsername(), loginUserDto.getUserId());
    }

    public ResponseEntity<List<LoginUserDto>> getAllUser(String token) {
        try {
            User user = authentication(token);
            if (user != null) {
                Long t1 = System.currentTimeMillis();
                List<LoginUserDto> list = new ArrayList<>();
                List<User> users = userRepository.findAll();
                users.forEach(u -> {
                    list.add(modelMapper.map(u, LoginUserDto.class));
                });
                Long t2 = System.currentTimeMillis();
                System.out.println(t2 - t1);
                return ResponseEntity.ok().body(list);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
