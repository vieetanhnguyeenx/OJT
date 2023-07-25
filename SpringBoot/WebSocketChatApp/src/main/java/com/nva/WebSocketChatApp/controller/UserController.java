package com.nva.WebSocketChatApp.controller;

import com.nva.WebSocketChatApp.storage.UserStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {
    @GetMapping("/registration/{userName}")
    public ResponseEntity<String> register(@PathVariable String userName) {
        System.out.println("Handling register");
        try {
            UserStorage.getInstance().setUsers(userName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        return UserStorage.getInstance().getUsers();
    }
}
