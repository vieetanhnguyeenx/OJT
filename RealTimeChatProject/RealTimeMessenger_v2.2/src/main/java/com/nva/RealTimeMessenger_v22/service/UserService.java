package com.nva.RealTimeMessenger_v22.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nva.RealTimeMessenger_v22.cache.TokenCache;
import com.nva.RealTimeMessenger_v22.dto.UserDto;
import com.nva.RealTimeMessenger_v22.entity.User;
import com.nva.RealTimeMessenger_v22.repository.MessageRepository;
import com.nva.RealTimeMessenger_v22.repository.RoomRepository;
import com.nva.RealTimeMessenger_v22.repository.UserRepository;
import com.nva.RealTimeMessenger_v22.util.TokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UserService implements IService {

    @Autowired
    private MessageRepository messageRepository;

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
    private ModelMapper modelMapper;

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


    public ResponseEntity<?> getRelatedUsers(String token) {
        try {
            UserDto userDto = authentication(token);
            if (userDto != null) {

                List<Integer> list = userRepository.findRelatedUsersId(userDto.getUserId());
                List<User> users = userRepository.findByUserId(list);
                Type listType = new TypeToken<List<UserDto>>() {
                }.getType();
                List<UserDto> postDtoList = modelMapper.map(users, listType);
                System.out.println("[getRelatedUsers]Return from database");
                return ResponseEntity.ok().body(postDtoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }

        System.out.println("getUserInformation(): Return null");
        return ResponseEntity.badRequest().body(null);
    }
}
