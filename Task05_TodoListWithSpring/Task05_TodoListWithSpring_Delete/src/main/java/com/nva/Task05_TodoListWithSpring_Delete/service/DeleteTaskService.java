package com.nva.Task05_TodoListWithSpring_Delete.service;

import com.nva.Task05_TodoListWithSpring_Delete.model.User;
import com.nva.Task05_TodoListWithSpring_Delete.repository.TodoRepository;
import com.nva.Task05_TodoListWithSpring_Delete.repository.UserRepository;
import com.nva.Task05_TodoListWithSpring_Delete.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class DeleteTaskService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public User authentication(String token) {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        User user = null;
        String tokenDecoded = null;
        try {
            tokenDecoded = new String(TokenHelper.decoder.decode(token));
        } catch (Exception e) {
            return null;
        }
        try {
            user = TokenHelper.gson.fromJson(tokenDecoded, User.class);
        } catch (Exception e) {
            return null;
        }
        if (user != null) {
            if (jedis.hexists("token", "user" + user.getId())) {
                return user;
            } else {
                User u = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
                if (u == null) {
                    return null;
                }
                jedis.hset("token", "user" + user.getId(), token);
                return user;
            }
        }
        return null;
    }

    public void sendMessageToTopic(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }

}
