package com.nva.Task05_TodoListWithSpring.controller;

import com.nva.Task05_TodoListWithSpring.dto.UserLoginDTO;
import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.*;
import com.nva.Task05_TodoListWithSpring.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/")
    private ModelAndView getLogin(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @PostMapping("/login")
    private ResponseEntity<String> postLogin(UserLoginDTO userLoginDTO) {
        Response response = null;
        if (userLoginDTO != null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Map<String, String> map = new HashMap<>();
            map.put("username", userLoginDTO.getUsername());
            map.put("password", userLoginDTO.getPassword());
            response = loginService.sendMessage(new Request(localDateTime.toString(), "/login", map, null), localDateTime.toString());
        }
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Location", response.getNewUrl());
        return new ResponseEntity<>(headers, response.getStatusCode());
    }


}
