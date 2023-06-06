package com.nva.Task05_TodoListWithSpring.controller;

import com.nva.Task05_TodoListWithSpring.dto.UserLoginDTO;
import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LogoutController {

    @GetMapping("/logout")
    private ResponseEntity<String> postLogin() {

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Location", "localhost:8888/");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
