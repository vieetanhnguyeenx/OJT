package com.nva.RealTimeMessenger_v21.service;

import com.google.gson.Gson;
import com.nva.RealTimeMessenger_v21.cache.TokenCache;
import com.nva.RealTimeMessenger_v21.dto.UserDto;
import com.nva.RealTimeMessenger_v21.entity.User;
import com.nva.RealTimeMessenger_v21.repository.UserRepository;
import com.nva.RealTimeMessenger_v21.util.TokenHelper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Gson gson;

    private final Jedis jedis = new Jedis();

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> authentication(String username, String password) {
        try {
            User u = userRepository.findByUsernameAndPassword(username, password);
            if (u != null) {
                UserDto loginUserDto = modelMapper.map(u, UserDto.class);
                String token = TokenHelper.encoder.encodeToString(gson.toJson(loginUserDto).getBytes());
                if (!TokenCache.tokenCache.containsKey(token)) {
                    TokenCache.tokenCache.put(token, loginUserDto);
                }
                return new ResponseEntity<String>(token, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<String>> test() {
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);

        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();
        List<UserDto> postDtoList = modelMapper.map(users, listType);
        List<String> tokenList = new ArrayList<>();
        postDtoList.forEach((userDto) -> {
            tokenList.add(TokenHelper.encoder.encodeToString(gson.toJson(userDto).getBytes()));
        });

        return new ResponseEntity<List<String>>(tokenList, HttpStatus.OK);

    }
}
