package com.nva.Task05_TodoListWithSpring.service;

import com.nva.Task05_TodoListWithSpring.model.Request;
import com.nva.Task05_TodoListWithSpring.model.Response;
import com.nva.Task05_TodoListWithSpring.util.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class DeleteService implements IService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Response sendMessage(Request request, String key) {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        String json = TokenHelper.gson.toJson(request);
        sendMessageToTopic(json, "delete-task-consumer");
        while (true) {
            if (jedis.hexists("mainCache", request.getId())) {
                Response response = TokenHelper.gson.fromJson(jedis.hget("mainCache", request.getId()), Response.class);
                System.out.println(response);
                System.out.println("Delete" + jedis.hdel("mainCache", request.getId()));
                return response;
            }
        }
    }

    private void sendMessageToTopic(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }
}
