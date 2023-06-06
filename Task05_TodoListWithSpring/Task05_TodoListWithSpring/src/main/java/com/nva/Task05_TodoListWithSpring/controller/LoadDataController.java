package com.nva.Task05_TodoListWithSpring.controller;

import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.Response;
import com.nva.Task05_TodoListWithSpring.model.Todo;
import com.nva.Task05_TodoListWithSpring.repository.TodoRepository;
import com.nva.Task05_TodoListWithSpring.service.LoadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LoadDataController {
    @Autowired
    private LoadDataService loadDataService;

    @Autowired
    private TodoRepository todoRepository;

    @PostMapping("/loaddata")
    private ResponseEntity<String> postLoadData(
            @RequestParam(value = "token", required = true) String token
    ) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Response response = loadDataService.sendMessage(
                new Request(localDateTime.toString(), "/loaddata", null, token),
                localDateTime.toString()
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getJsonData());

    }
}
