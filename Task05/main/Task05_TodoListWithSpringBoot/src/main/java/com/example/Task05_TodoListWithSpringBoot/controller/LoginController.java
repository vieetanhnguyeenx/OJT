package com.example.Task05_TodoListWithSpringBoot.controller;

import com.example.Task05_TodoListWithSpringBoot.dto.UserLoginDTO;
import com.example.Task05_TodoListWithSpringBoot.model.Request;
import com.example.Task05_TodoListWithSpringBoot.model.Response;
import com.example.Task05_TodoListWithSpringBoot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    Logger logger
            = Logger.getLogger(
            LoginController.class.getName());

    @GetMapping("/login")
    public String getLogin() {
        return "login.html";
    }

    @PostMapping("/login")
    public String postLogin(UserLoginDTO userLoginDTO) {
        if (userLoginDTO != null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Map<String, String> map = new HashMap<>();
            map.put("username", userLoginDTO.getUsername());
            map.put("password", userLoginDTO.getPassword());
            Response response = loginService.sendMessage(new Request(localDateTime.toString(), "/login", map, null), localDateTime.toString());
            System.out.println(response);
        }

        return "redirect:/todolist";
    }


}
