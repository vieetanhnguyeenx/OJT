package com.nva.Task05_TodoListWithSpring.controller;

import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.Response;
import com.nva.Task05_TodoListWithSpring.service.AddTaskService;
import com.nva.Task05_TodoListWithSpring.service.ChangeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AddTaskController {
    @Autowired
    private AddTaskService addTaskService;


    @PostMapping("/addNewTask")
    private ResponseEntity<String> postLoadData(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "title", required = true) String title
    ) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        Response response = addTaskService.sendMessage(
                new Request(localDateTime.toString(), "/addNewTask",
                        map, token),
                localDateTime.toString()
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getTextData());

    }
}
