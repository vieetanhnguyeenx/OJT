package com.example.Task05_TodoListWithSpringBoot.messageQueue;

import com.example.Task05_TodoListWithSpringBoot.model.Response;

import java.util.HashMap;
import java.util.Map;

public class MessageQueue {
    public static Map<String, Response> messageQueue = new HashMap<>();
}
