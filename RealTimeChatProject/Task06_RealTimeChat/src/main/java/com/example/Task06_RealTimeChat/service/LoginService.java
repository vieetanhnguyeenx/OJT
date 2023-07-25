package com.example.Task06_RealTimeChat.service;

import com.example.Task06_RealTimeChat.dto.LoginUserDto;
import com.example.Task06_RealTimeChat.model.User;
import com.example.Task06_RealTimeChat.repository.UserRepository;
import com.example.Task06_RealTimeChat.util.TokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class LoginService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> authentication(String username, String password) {
        User u = userRepository.findByUsernameAndPassword(username, password);
        if (u != null) {
            LoginUserDto loginUserDto = modelMapper.map(u, LoginUserDto.class);
            String token = TokenHelper.encoder.encodeToString(TokenHelper.gson.toJson(loginUserDto).getBytes());
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
