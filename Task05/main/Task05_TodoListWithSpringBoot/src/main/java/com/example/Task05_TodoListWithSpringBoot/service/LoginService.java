package com.example.Task05_TodoListWithSpringBoot.service;

import com.example.Task05_TodoListWithSpringBoot.messageQueue.MessageQueue;
import com.example.Task05_TodoListWithSpringBoot.model.Request;
import com.example.Task05_TodoListWithSpringBoot.model.Response;
import com.example.Task05_TodoListWithSpringBoot.repository.TodoRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements IService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Response sendMessage(Request request, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        sendMessageToTopic(json, "login-request-serv");
        while (true) {
            if (MessageQueue.messageQueue.containsKey(key)) {
                System.out.println(MessageQueue.messageQueue.get(key));
                Response response = MessageQueue.messageQueue.get(key);
                return response;
            }
        }
    }

    private void sendMessageToTopic(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }
}
