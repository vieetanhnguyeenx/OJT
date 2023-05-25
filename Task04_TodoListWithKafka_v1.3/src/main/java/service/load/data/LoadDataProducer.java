package service.load.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import context.TodoDAO;
import context.UserDAO;
import core.TokenUserCache;
import helper.HTTPServerHelper;
import helper.JsonHelper;
import helper.TokenHelper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class LoadDataProducer extends Thread {
    @Override
    public void run() {
        Properties producerProperties = new Properties();
        Gson gson = new Gson();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "load-data-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(5 * 1024));
        producerProperties.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
            producer.send(new ProducerRecord<>("login-response-serv", "test"));
            producer.flush();
        }


        TodoDAO.addNewTaskAndReturn(100, "test");
        HTTPServerHelper.getParameter("123", "title");
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.hgetAll("user" + 1);
        while (true) {
            if (LoadDataData.loadDataData.size() > 0) {
                for (Map.Entry<Integer, Request> data : LoadDataData.loadDataData.entrySet()) {
                    Integer key = data.getKey();
                    Request val = data.getValue();
                    Long t1 = System.currentTimeMillis();
                    if (val.getMethod().equalsIgnoreCase("post")) {
                        Long t3  = System.currentTimeMillis();
                        String token = HTTPServerHelper.getParameter(val.getPayload(), "token");
                        Long t7 = System.currentTimeMillis();
                        System.out.println(t7 - t3);

                        Long t8 = System.currentTimeMillis();
                        User u1 = TokenHelper.tokenToUser(token);
                        Long t9 = System.currentTimeMillis();

                        System.out.println(t9 - t8);

                        System.out.println(u1);
                        boolean isAuthenticated = false;
                        if (u1 != null && jedis.hexists("token", "user" + u1.getId())) {
                            isAuthenticated = true;
                        } else {
                            try {
                                u1 = UserDAO.authenticationToken(token);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            if (u1 != null) {
                                System.out.println("not null");
                                isAuthenticated = true;
                                jedis.hset("token", "user" + u1.getId(), token);
                            }
                        }
                        Long t4  = System.currentTimeMillis();
                        Long t5  = System.currentTimeMillis();
                        if (isAuthenticated) {
                            List<Todo> todoList = new ArrayList<>();
                            Long cacheSize = jedis.hlen("user" + u1.getId());
                            if (cacheSize == 0) {
                                todoList = TodoDAO.getTodoList(u1.getId());
                                System.out.println("db");
                                if (todoList.size() > 0) {
                                    for (Todo todo: todoList) {
                                        try {
                                            jedis.hset("user" + u1.getId(), String.valueOf(todo.getId()), JsonHelper.stringgify(JsonHelper.toJson(todo)));
                                        } catch (JsonProcessingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } else {
                                Long t16 = System.currentTimeMillis();
                                Map<String, String> cacheList = jedis.hgetAll("user" + u1.getId());
                                System.out.println(cacheList);
                                for (Map.Entry<String, String> entry: cacheList.entrySet()) {
                                    try {
                                        System.out.println(entry.getKey());
                                        System.out.println("cache");
                                        todoList.add(JsonHelper.fromJson(JsonHelper.parse(entry.getValue()), Todo.class));
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Long t17 = System.currentTimeMillis();
                                System.out.println( "Cache" + String.valueOf(t17 - t16));
                            }
                            Long t14  = System.currentTimeMillis();

                            JsonNode jsonNode = JsonHelper.toJson(todoList);
                            String todoJson = null;
                            try {
                                todoJson = JsonHelper.stringgify(jsonNode);
                            } catch (JsonProcessingException e) {
                                System.out.println("Stringgify Error");
                            }
                            Long t15  = System.currentTimeMillis();
                            System.out.println("stringgify:" + String.valueOf(t15 - t14));
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, null, todoJson, token);
                            Long t12  = System.currentTimeMillis();
                            String json = gson.toJson(response);
                            System.out.println(json);
                            Long t13  = System.currentTimeMillis();
                            System.out.println("To Json:" + String.valueOf(t13 - t12));
                            Long t10  = System.currentTimeMillis();
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                            Long t11  = System.currentTimeMillis();
                            System.out.println("Produce:" + String.valueOf(t10 - t11));
                        } else {
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, "accessDenied.html", null, null);
                            String json = gson.toJson(response);
                            System.out.println(json);
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                        }
                        Long t6  = System.currentTimeMillis();
                        Long t2 = System.currentTimeMillis();
                        System.out.println("All" + String.valueOf(t2 - t1));
                        System.out.println("Authentication" + String.valueOf(t4 - t3));
                        System.out.println("Response" + String.valueOf(t6 - t5));
                        LoadDataData.loadDataData.remove(key);
                    }
                }
            }
        }
    }
}
