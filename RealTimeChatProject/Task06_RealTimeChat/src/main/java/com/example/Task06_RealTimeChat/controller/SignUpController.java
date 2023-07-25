package com.example.Task06_RealTimeChat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SignUpController {

    @GetMapping("/signup")
    private ModelAndView getLoginForm2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup.html");
        return modelAndView;
    }

}
