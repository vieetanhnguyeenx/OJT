package com.nva.Task05_TodoListWithSpring.service;
import com.nva.Task05_TodoListWithSpring.*;
import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.Response;


public interface IService {
    public Response sendMessage(Request request, String key);
    
}
