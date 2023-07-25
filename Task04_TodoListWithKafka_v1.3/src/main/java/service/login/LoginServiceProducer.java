package service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import context.TodoDAO;
import context.UserDAO;
import core.TokenUserCache;
import helper.HTTPServerHelper;
import helper.JsonHelper;
import model.Request;
import model.Response;
import model.Todo;
import model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

public class LoginServiceProducer extends Thread {

    @Override
    public void run() {
        Properties producerProperties = new Properties();
        Gson gson = new Gson();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "login-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(5 * 1024));
        TodoDAO.addNewTaskAndReturn(100, "test");
        HTTPServerHelper.getParameter("123", "title");
        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
            producer.send(new ProducerRecord<>("login-response-serv", "test"));
            producer.flush();
        }

        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        while (true) {
            if (LoginData.data.size() > 0) {
                for (Map.Entry<Integer, Request> data : LoginData.data.entrySet()) {
                    Integer key = data.getKey();
                    Request val = data.getValue();
                    if (val.getMethod().equalsIgnoreCase("get")) {
                        Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, "login.html", null, null);
                        String json = gson.toJson(response);
                        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                            producer.send(new ProducerRecord<>("login-response-serv", json));
                            producer.flush();
                        }
                    } else if (val.getMethod().equalsIgnoreCase("post")) {
                        String username = HTTPServerHelper.getParameter(val.getPayload(), "username");
                        String password = HTTPServerHelper.getParameter(val.getPayload(), "password");
                        User user = UserDAO.getUser(username, password);
                        if (user != null) {
                            JsonNode jsonNode = JsonHelper.toJson(user);
                            String jString = null;
                            try {
                                jString = JsonHelper.stringgify(jsonNode);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            Base64.Encoder encoder = Base64.getEncoder();
                            String token = encoder.encodeToString(jString.getBytes());
                            if (!jedis.hexists("token", "user" + user.getId())) {
                                jedis.hset("token", "user" + user.getId(), token);
                            }
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_FOUND, "http://localhost:8888/todolist?token" + "=" + token, null, null, null);
                            JsonNode jsonNode1 = JsonHelper.toJson(response);
                            String jsonResponse = null;
                            try {
                                jsonResponse = JsonHelper.stringgify(jsonNode1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            System.out.println(jsonResponse);
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", jsonResponse));
                                producer.flush();
                            }
                            List<Todo> todoList = new ArrayList<>();
                            Long cacheSize = jedis.hlen("user" + user.getId());
                            if (cacheSize == 0) {
                                todoList = TodoDAO.getTodoList(user.getId());
                                if (todoList.size() > 0) {
                                    for (Todo todo : todoList) {
                                        try {
                                            jedis.hset("user" + user.getId(), String.valueOf(todo.getId()), JsonHelper.stringgify(JsonHelper.toJson(todo)));
                                        } catch (JsonProcessingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } else {
                                Map<String, String> cacheList = jedis.hgetAll("user" + user.getId());
                                for (Map.Entry<String, String> entry : cacheList.entrySet()) {
                                    try {
                                        todoList.add(JsonHelper.fromJson(JsonHelper.parse(entry.getValue()), Todo.class));
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_FOUND, "http://localhost:8888/login", null, null, null);
                            String json = gson.toJson(response);
                            System.out.println(json);
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                        }
                    }
                    LoginData.data.remove(key);
                }
            }
        }
    }
}
