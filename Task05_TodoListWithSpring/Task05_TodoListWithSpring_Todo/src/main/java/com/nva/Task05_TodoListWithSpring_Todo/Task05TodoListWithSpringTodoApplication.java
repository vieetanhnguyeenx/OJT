package com.nva.Task05_TodoListWithSpring_Todo;

import com.nva.Task05_TodoListWithSpring_Todo.model.Request;
import com.nva.Task05_TodoListWithSpring_Todo.model.Response;
import com.nva.Task05_TodoListWithSpring_Todo.model.Todo;
import com.nva.Task05_TodoListWithSpring_Todo.model.User;
import com.nva.Task05_TodoListWithSpring_Todo.repository.TodoRepository;
import com.nva.Task05_TodoListWithSpring_Todo.service.AddTaskService;
import com.nva.Task05_TodoListWithSpring_Todo.util.TokenHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Task05TodoListWithSpringTodoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringTodoApplication.class, args);

        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        AddTaskService addTaskService = context.getBean(AddTaskService.class);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);

        Map<String, String> dataMap;
        while (true) {
            if (jedis.hlen("addTaskConsumer") > 0) {
                dataMap = jedis.hgetAll("addTaskConsumer");
                for (Map.Entry<String, String> data : dataMap.entrySet()) {
                    String key = data.getKey();
                    Request request = TokenHelper.gson.fromJson(data.getValue(), Request.class);
                    User user = addTaskService.authentication(request.getToken());
                    if (user != null) {
                        String s = request.getParameter().get("title");
                        if (s != null) {
                            Todo todo = new Todo(s, false, user.getId());
                            todoRepository.save(todo);
                        }
                        Response response = new Response(key, request.getUrl(), HttpStatus.OK,
                                null, null, null, "Add Success");
                        String jsonResponse = TokenHelper.gson.toJson(response);
                        addTaskService.sendMessageToTopic(jsonResponse, "login-response-serv");

                        List<Todo> todoList = todoRepository.findByUserId(user.getId());
                        String jsonData = TokenHelper.gson.toJson(todoList);
                        jedis.hset("loadDataCache", "user" + user.getId(), jsonData);
                    } else {
                        Response response = new Response(key, request.getUrl(), HttpStatus.FORBIDDEN,
                                null,
                                null, null, "Add Fail");

                        String jsonResponse = TokenHelper.gson.toJson(response);
                        addTaskService.sendMessageToTopic(jsonResponse, "login-response-serv");
                    }

                    jedis.hdel("addTaskConsumer", key);
                    dataMap.remove(key);
                }
            }
        }

    }

}
