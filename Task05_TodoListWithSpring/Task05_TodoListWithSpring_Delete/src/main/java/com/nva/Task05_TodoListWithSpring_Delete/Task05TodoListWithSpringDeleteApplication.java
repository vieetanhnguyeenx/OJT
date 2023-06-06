package com.nva.Task05_TodoListWithSpring_Delete;

import com.nva.Task05_TodoListWithSpring_Delete.model.Request;
import com.nva.Task05_TodoListWithSpring_Delete.model.Response;
import com.nva.Task05_TodoListWithSpring_Delete.model.Todo;
import com.nva.Task05_TodoListWithSpring_Delete.model.User;
import com.nva.Task05_TodoListWithSpring_Delete.repository.TodoRepository;
import com.nva.Task05_TodoListWithSpring_Delete.service.DeleteTaskService;
import com.nva.Task05_TodoListWithSpring_Delete.util.TokenHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Task05TodoListWithSpringDeleteApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringDeleteApplication.class, args);

		JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
		Jedis jedis = jedisPool.getResource();
		DeleteTaskService changeStatusService = context.getBean(DeleteTaskService.class);
		TodoRepository todoRepository = context.getBean(TodoRepository.class);
		Map<String, String> dataMap;
		while (true) {
			if (jedis.hlen("deleteTaskConsumer") > 0) {
				dataMap = jedis.hgetAll("deleteTaskConsumer");
				for(Map.Entry<String, String> data: dataMap.entrySet()) {
					String key = data.getKey();
					Request request = TokenHelper.gson.fromJson(data.getValue(), Request.class);
					User user = changeStatusService.authentication(request.getToken());
					String jsonData;
					if (user != null) {
						int id = Integer.parseInt(request.getParameter().get("id"));
						Todo todo = todoRepository.findByIdAndUserID(id, user.getId());
						if (todo != null) {
							todoRepository.delete(todo);
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

					jedis.hdel("deleteTaskConsumer", key);
					dataMap.remove(key);
				}
			}
		}
	}

}
