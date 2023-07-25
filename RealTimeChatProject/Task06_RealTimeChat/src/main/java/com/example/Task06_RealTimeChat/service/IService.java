package com.example.Task06_RealTimeChat.service;

import com.example.Task06_RealTimeChat.model.User;
import org.springframework.http.ResponseEntity;

public interface IService {
    public User authentication(String token);

}
