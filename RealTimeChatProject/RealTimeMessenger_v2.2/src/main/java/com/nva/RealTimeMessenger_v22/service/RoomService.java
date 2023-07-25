package com.nva.RealTimeMessenger_v22.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nva.RealTimeMessenger_v22.cache.TokenCache;
import com.nva.RealTimeMessenger_v22.dto.UserDto;
import com.nva.RealTimeMessenger_v22.entity.Room;
import com.nva.RealTimeMessenger_v22.entity.User;
import com.nva.RealTimeMessenger_v22.entity.UserRoom;
import com.nva.RealTimeMessenger_v22.repository.RoomRepository;
import com.nva.RealTimeMessenger_v22.repository.UserRepository;
import com.nva.RealTimeMessenger_v22.repository.UserRoomRepository;
import com.nva.RealTimeMessenger_v22.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class RoomService implements IService {

    @Autowired
    private Gson gson;


    private final Jedis jedis = new Jedis();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Override
    public UserDto authentication(String token) {
        try {
            if (TokenCache.tokenCache.containsKey(token)) {
                System.out.println("TokenCache");
                return TokenCache.tokenCache.get(token);
            } else {
                UserDto userDto = gson.fromJson(new String(TokenHelper.decoder.decode(token)), UserDto.class);
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

    public ResponseEntity<?> fetchRoom(String token) {
        try {
            UserDto userDto = authentication(token);
            if (userDto != null) {
                List<Room> list = roomRepository.findByUserId(userDto.getUserId());
                jedis.hset("roomCache", userDto.getUserId() + "", gson.toJson(list));
                System.out.println("[fetchRoom] Return from database");
                return ResponseEntity.ok().body(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("fetchRoom(): Return null");
        return ResponseEntity.badRequest().body(null);
    }

    public ResponseEntity<?> getRoomInformation(String token, int roomId) {
        try {
            UserDto userDto = authentication(token);
            if (userDto != null) {
                Room room = roomRepository.findByRoom_id(roomId);
                return ResponseEntity.ok().body(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("fetchRoom(): Return null");
        return ResponseEntity.badRequest().body(null);
    }

    public ResponseEntity<?> getUserRoomInfo(String token, int roomId) {
        try {
            UserDto userDto = authentication(token);
            System.out.println(userDto);
            if (userDto != null) {
                List<UserRoom> list = userRoomRepository.getUserRoomByRoomId(roomId);
                return ResponseEntity.ok().body(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("getUserRoomInfo(): Return null");
        return ResponseEntity.badRequest().body(null);
    }
}
