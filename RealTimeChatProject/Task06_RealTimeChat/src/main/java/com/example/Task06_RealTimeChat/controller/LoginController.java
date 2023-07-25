package com.example.Task06_RealTimeChat.controller;

import com.example.Task06_RealTimeChat.config.ModelMapperConfig;
import com.example.Task06_RealTimeChat.service.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;


    @GetMapping("/login")
    private ModelAndView getLoginForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @GetMapping("/")
    private ModelAndView getLoginForm2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }


    @PostMapping("/login")
    private ResponseEntity<String> login(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password) {
        return loginService.authentication(username, password);
    }
}
