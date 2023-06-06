package com.nva.Task05_TodoListWithSpring_ChangeStatus;

import com.nva.Task05_TodoListWithSpring_ChangeStatus.model.Request;
import com.nva.Task05_TodoListWithSpring_ChangeStatus.model.Todo;
import com.nva.Task05_TodoListWithSpring_ChangeStatus.model.User;
import com.nva.Task05_TodoListWithSpring_ChangeStatus.model.Response;
import com.nva.Task05_TodoListWithSpring_ChangeStatus.repository.TodoRepository;
import com.nva.Task05_TodoListWithSpring_ChangeStatus.service.ChangeStatusService;
import com.nva.Task05_TodoListWithSpring_ChangeStatus.util.TokenHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Task05TodoListWithSpringChangeStatusApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringChangeStatusApplication.class, args);
		JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
		Jedis jedis = jedisPool.getResource();
		ChangeStatusService changeStatusService = context.getBean(ChangeStatusService.class);
		TodoRepository todoRepository = context.getBean(TodoRepository.class);
		Map<String, String> dataMap;
		while (true) {
			if (jedis.hlen("changeStatusConsumer") > 0) {
				dataMap = jedis.hgetAll("changeStatusConsumer");
				for(Map.Entry<String, String> data: dataMap.entrySet()) {
					String key = data.getKey();
					Request request = TokenHelper.gson.fromJson(data.getValue(), Request.class);
					User user = changeStatusService.authentication(request.getToken());
					String jsonData;
					if (user != null) {
						int id = Integer.parseInt(request.getParameter().get("id"));
						Todo todo = todoRepository.findByIdAndUserID(id, user.getId());
						if (todo != null) {
							todo.setStatus(!todo.isStatus());
							todoRepository.save(todo);
						}
						Response response = new Response(key, request.getUrl(), HttpStatus.OK,
								null, null, null, "Success");
						String jsonResponse = TokenHelper.gson.toJson(response);
						changeStatusService.sendMessageToTopic(jsonResponse, "login-response-serv");
						List<Todo> todoList = todoRepository.findByUserId(user.getId());
						jsonData = TokenHelper.gson.toJson(todoList);
						jedis.hset("loadDataCache", "user" + user.getId(), jsonData);
					} else {
						Response response = new Response(key, request.getUrl(), HttpStatus.FORBIDDEN,
								null,
								null, null, "Fail");

						String jsonResponse = TokenHelper.gson.toJson(response);
						changeStatusService.sendMessageToTopic(jsonResponse, "login-response-serv");
					}

					jedis.hdel("changeStatusConsumer", key);
					dataMap.remove(key);
				}
			}
		}

	}

}
