package com.example.Task05_TodoListWithSpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoListController {
    @GetMapping("/todolist")
    public String getTodoList() {
        return "todolist.html";
    }
}
