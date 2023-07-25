package com.nva.Task05_TodoListWithSpring_Login;

import com.nva.Task05_TodoListWithSpring_Login.message.Redis;
import com.nva.Task05_TodoListWithSpring_Login.model.Request;
import com.nva.Task05_TodoListWithSpring_Login.model.Response;
import com.nva.Task05_TodoListWithSpring_Login.model.User;
import com.nva.Task05_TodoListWithSpring_Login.service.LoginService;
import com.nva.Task05_TodoListWithSpring_Login.util.TokenHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Task05TodoListWithSpringLoginApplication {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringLoginApplication.class, args);
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        LoginService loginService = context.getBean(LoginService.class);
        Map<String, String> dataMap;
        while (true) {
            if (jedis.hlen("loginCache") > 0) {
                dataMap = jedis.hgetAll("loginCache");
                for (Map.Entry<String, String> data : dataMap.entrySet()) {
                    String key = data.getKey();
                    Request request = TokenHelper.gson.fromJson(data.getValue(), Request.class);
                    User user = loginService.authentication(request.getParameter().get("username"), request.getParameter().get("password"));
                    if (user != null) {
                        String jsonUser = TokenHelper.gson.toJson(user);
                        String token = TokenHelper.encoder.encodeToString(jsonUser.getBytes());
                        if (!jedis.hexists("token", "user" + user.getId())) {
                            jedis.hset("token", "user" + user.getId(), token);
                        }

                        Response response = new Response(key, request.getUrl(), HttpStatus.FOUND,
                                "http://localhost:8888/todoList?token" + "=" + token,
                                null, null, null);

                        String jsonResponse = TokenHelper.gson.toJson(response);
                        loginService.sendMessageToTopic(jsonResponse, "login-response-serv");
                    } else {
                        Response response = new Response(key, request.getUrl(), HttpStatus.FOUND,
                                "http://localhost:8888/",
                                null, null, null);

                        String jsonResponse = TokenHelper.gson.toJson(response);
                        loginService.sendMessageToTopic(jsonResponse, "login-response-serv");
                    }
                    jedis.hdel("loginCache", key);
                    dataMap.remove(key);
                }
            }
        }


//		UserRepository userRepository = context.getBean(UserRepository.class);
//		TodoRepository todoRepository = context.getBean(TodoRepository.class);

//		todoRepository.findAll().forEach(System.out::println);
//		userRepository.findAll();
//		LoginService loginService = new LoginService();
//		loginService.start();
    }

}
