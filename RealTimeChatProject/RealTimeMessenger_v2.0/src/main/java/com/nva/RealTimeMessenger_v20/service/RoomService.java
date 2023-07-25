package com.nva.RealTimeMessenger_v20.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nva.RealTimeMessenger_v20.dto.UserDto;
import com.nva.RealTimeMessenger_v20.model.Room;
import com.nva.RealTimeMessenger_v20.model.User;
import com.nva.RealTimeMessenger_v20.repository.RoomRepository;
import com.nva.RealTimeMessenger_v20.repository.UserRepository;
import com.nva.RealTimeMessenger_v20.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class RoomService implements IService {
    @Autowired
    private Gson gson;

    @Autowired
    private Jedis jedis;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public UserDto authentication(String token) {
        try {
            if (jedis.hexists("tokenCache", token)) {
                String userDtoJson = jedis.hget("tokenCache", token);
                return gson.fromJson(userDtoJson, UserDto.class);
            } else {
                UserDto userDto = TokenHelper.gson.fromJson(new String(TokenHelper.decoder.decode(token)), UserDto.class);
                User user = userRepository.findByUserIdAndUsername(userDto.getUserId(), userDto.getUsername());
                if (user != null) {
                    return userDto;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ResponseEntity<List<Room>> fetchRoom(String token) {
        try {
            UserDto userDto = authentication(token);
            if (userDto != null) {
                if (jedis.hexists("roomCache", String.valueOf(userDto.getUserId()))) {
                    String roomJson = jedis.hget("roomCache", String.valueOf(userDto.getUserId()));
                    List<Room> rooms = objectMapper.readValue(roomJson, new TypeReference<List<Room>>() {
                    });
                    System.out.println("Return from jedis");
                    return ResponseEntity.ok().body(rooms);
                } else {
                    List<Room> list = roomRepository.findByUserId(userDto.getUserId());
                    jedis.hset("roomCache", userDto.getUserId() + "", objectMapper.writeValueAsString(list));
                    System.out.println("Return from database");
                    return ResponseEntity.ok().body(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("Return null");
        return ResponseEntity.badRequest().body(null);
    }
}
