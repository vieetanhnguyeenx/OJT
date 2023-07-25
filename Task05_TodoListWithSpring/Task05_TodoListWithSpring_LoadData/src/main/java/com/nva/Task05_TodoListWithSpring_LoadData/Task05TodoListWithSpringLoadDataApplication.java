package com.nva.Task05_TodoListWithSpring_LoadData;

import com.nva.Task05_TodoListWithSpring_LoadData.model.Request;
import com.nva.Task05_TodoListWithSpring_LoadData.model.Response;
import com.nva.Task05_TodoListWithSpring_LoadData.model.Todo;
import com.nva.Task05_TodoListWithSpring_LoadData.model.User;
import com.nva.Task05_TodoListWithSpring_LoadData.repository.TodoRepository;
import com.nva.Task05_TodoListWithSpring_LoadData.service.LoadDataService;
import com.nva.Task05_TodoListWithSpring_LoadData.util.TokenHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Task05TodoListWithSpringLoadDataApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringLoadDataApplication.class, args);
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        LoadDataService loadDataService = context.getBean(LoadDataService.class);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);

        Map<String, String> dataMap;
        while (true) {
            if (jedis.hlen("loadDataConsumer") > 0) {
                dataMap = jedis.hgetAll("loadDataConsumer");
                for (Map.Entry<String, String> data : dataMap.entrySet()) {
                    String key = data.getKey();
                    Request request = TokenHelper.gson.fromJson(data.getValue(), Request.class);
                    User user = loadDataService.authentication(request.getToken());
                    String jsonData;
                    if (user != null) {
                        if (jedis.hexists("loadDataCache", "user" + user.getId())) {
                            jsonData = jedis.hget("loadDataCache", "user" + user.getId());
                        } else {
                            List<Todo> todoList = todoRepository.findByUserId(user.getId());
                            jsonData = TokenHelper.gson.toJson(todoList);
                            jedis.hset("loadDataCache", "user" + user.getId(), jsonData);
                        }

                        Response response = new Response(key, request.getUrl(), HttpStatus.OK,
                                null, null, jsonData, null);

                        String jsonResponse = TokenHelper.gson.toJson(response);
                        loadDataService.sendMessageToTopic(jsonResponse, "login-response-serv");
                    } else {
                        Response response = new Response(key, request.getUrl(), HttpStatus.FORBIDDEN,
                                null,
                                null, null, null);

                        String jsonResponse = TokenHelper.gson.toJson(response);
                        loadDataService.sendMessageToTopic(jsonResponse, "login-response-serv");
                    }

                    jedis.hdel("loadDataConsumer", key);
                    dataMap.remove(key);
                }
            }
        }
    }

}
