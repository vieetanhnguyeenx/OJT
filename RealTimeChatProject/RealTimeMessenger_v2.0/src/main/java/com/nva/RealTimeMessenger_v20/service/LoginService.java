package com.nva.RealTimeMessenger_v20.service;

import com.google.gson.Gson;
import com.nva.RealTimeMessenger_v20.dto.UserDto;
import com.nva.RealTimeMessenger_v20.model.User;
import com.nva.RealTimeMessenger_v20.repository.UserRepository;
import com.nva.RealTimeMessenger_v20.util.TokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class LoginService {
    @Autowired
    private Gson gson;

    @Autowired
    private Jedis jedis;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> authentication(String username, String password) {
        try {
            User u = userRepository.findByUsernameAndPassword(username, password);
            if (u != null) {
                UserDto loginUserDto = modelMapper.map(u, UserDto.class);
                String token = TokenHelper.encoder.encodeToString(TokenHelper.gson.toJson(loginUserDto).getBytes());
                String userJson = gson.toJson(loginUserDto);
                if (!jedis.hexists("tokenCache", token)) {
                    jedis.hset("tokenCache", token, userJson);
                }
                return new ResponseEntity<String>(token, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
