package com.nva.Task05_TodoListWithSpring.controller;

import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.Response;
import com.nva.Task05_TodoListWithSpring.service.ChangeStatusService;
import com.nva.Task05_TodoListWithSpring.service.EditTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EditTaskController {

    @Autowired
    private EditTaskService editTaskService;


    @PostMapping("/editTask") //title
    private ResponseEntity<String> postLoadData(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "title", required = true) String title
    ) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        Response response = editTaskService.sendMessage(
                new Request(localDateTime.toString(), "/changeStatus",
                        map, token),
                localDateTime.toString()
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getTextData());

    }
}
