package com.nva.Task05_TodoListWithSpring_Login.service;

import com.nva.Task05_TodoListWithSpring_Login.model.User;
import com.nva.Task05_TodoListWithSpring_Login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public User authentication(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;

    }

    public void sendMessageToTopic(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }
}
