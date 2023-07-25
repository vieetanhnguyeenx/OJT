package com.nva.Task05_TodoListWithSpring.controller;

import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.Response;
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
public class ChangeStatusController {
    @Autowired
    private ChangeStatusService changeStatusService;


    @PostMapping("/changeStatus")
    private ResponseEntity<String> postLoadData(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "id", required = true) String id
    ) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        Response response = changeStatusService.sendMessage(
                new Request(localDateTime.toString(), "/changeStatus",
                        map, token),
                localDateTime.toString()
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getTextData());

    }
}
