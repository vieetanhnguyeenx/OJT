package com.nva.RealTimeMessenger_v21.controller;

import com.nva.RealTimeMessenger_v21.repository.UserRepository;
import com.nva.RealTimeMessenger_v21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getRelatedUsers")
    private ResponseEntity<?> getAllRelatedUser(
            @RequestParam(value = "token", required = true) String token
    ) {
        return userService.getRelatedUsers(token);
    }
}
