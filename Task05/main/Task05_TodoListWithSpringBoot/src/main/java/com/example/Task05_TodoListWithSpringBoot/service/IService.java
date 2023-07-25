package com.example.Task05_TodoListWithSpringBoot.service;

import com.example.Task05_TodoListWithSpringBoot.model.Request;
import com.example.Task05_TodoListWithSpringBoot.model.Response;

public interface IService {
    public Response sendMessage(Request request, String key);

}
