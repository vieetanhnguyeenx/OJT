package service.delete.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import context.TodoDAO;
import context.UserDAO;
import core.TokenUserCache;
import helper.HTTPServerHelper;
import helper.TokenHelper;
import model.Request;
import model.Response;
import model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Properties;

public class DeleteTaskProducer extends Thread {
    @Override
    public void run() {
        Properties producerProperties = new Properties();
        Gson gson = new Gson();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "delete-task-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(5 * 1024));
        TodoDAO.deleteTask(0, 1);
        Long t11 = System.currentTimeMillis();
        HTTPServerHelper.getParameter("123", "title");
        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
            producer.send(new ProducerRecord<>("login-response-serv", "test"));
            producer.flush();
        }
        Long t12 = System.currentTimeMillis();
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        while (true) {
            if (DeleteTaskData.data.size() > 0) {
                for (Map.Entry<Integer, Request> data : DeleteTaskData.data.entrySet()) {
                    Integer key = data.getKey();
                    Request val = data.getValue();
                    System.out.println(val);

                    if (val.getMethod().equalsIgnoreCase("post")) {
                        Long t3 = System.currentTimeMillis();
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

                        if (isAuthenticated) {
                            int taskId = Integer.parseInt(HTTPServerHelper.getParameter(val.getPayload(), "id"));
                            jedis.hdel("user" + u1.getId(), String.valueOf(taskId));
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, null, null, token);
                            response.setTextData("Delete Success");
                            String json = gson.toJson(response);
                            System.out.println(json);
                            Long t13 = System.currentTimeMillis();
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                            TodoDAO.deleteTask(u1.getId(), taskId);
                            Long t14 = System.currentTimeMillis();
                            System.out.println(t14 - t13);
                        } else {
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, "accessDenied.html", null, null);
                            String json = gson.toJson(response);
                            System.out.println(json);
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                        }
                        DeleteTaskData.data.remove(key);
                    }
                }
            }
        }
    }
}
