package com.nva.Task05_TodoListWithSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TodoListController {
    @GetMapping("/todoList")
    public ModelAndView getTodoList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("todoList.html");
        return modelAndView;
    }
}
