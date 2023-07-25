package com.example.Task06_RealTimeChat.controller;

import com.example.Task06_RealTimeChat.dto.LoginUserDto;
import com.example.Task06_RealTimeChat.model.User;
import com.example.Task06_RealTimeChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    private ResponseEntity<List<LoginUserDto>> getAllUSer(
            @RequestParam(value = "token", required = true) String token
    ) {
        return userService.getAllUser(token);
    }
}
