package com.nva.Task05_TodoListWithSpring_Edit;

import com.nva.Task05_TodoListWithSpring_Edit.model.Request;
import com.nva.Task05_TodoListWithSpring_Edit.model.Response;
import com.nva.Task05_TodoListWithSpring_Edit.model.Todo;
import com.nva.Task05_TodoListWithSpring_Edit.model.User;
import com.nva.Task05_TodoListWithSpring_Edit.repository.TodoRepository;
import com.nva.Task05_TodoListWithSpring_Edit.service.EditTaskService;
import com.nva.Task05_TodoListWithSpring_Edit.util.TokenHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Task05TodoListWithSpringEditApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringEditApplication.class, args);

		JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
		Jedis jedis = jedisPool.getResource();
		EditTaskService editTaskService = context.getBean(EditTaskService.class);
		TodoRepository todoRepository = context.getBean(TodoRepository.class);
		Map<String, String> dataMap;
		while (true) {
			if (jedis.hlen("editTaskConsumer") > 0) {
				dataMap = jedis.hgetAll("editTaskConsumer");
				for(Map.Entry<String, String> data: dataMap.entrySet()) {
					String key = data.getKey();
					Request request = TokenHelper.gson.fromJson(data.getValue(), Request.class);
					User user = editTaskService.authentication(request.getToken());
					if (user != null) {
						int id = Integer.parseInt(request.getParameter().get("id"));
						Todo todo = todoRepository.findByIdAndUserID(id, user.getId());
						if (todo != null) {
							todo.setTitle(request.getParameter().get("title"));
							todoRepository.save(todo);
						}
						Response response = new Response(key, request.getUrl(), HttpStatus.OK,
								null, null, null, "Success");
						String jsonResponse = TokenHelper.gson.toJson(response);
						editTaskService.sendMessageToTopic(jsonResponse, "login-response-serv");

						List<Todo> todoList = todoRepository.findByUserId(user.getId());
						String jsonData = TokenHelper.gson.toJson(todoList);
						jedis.hset("loadDataCache", "user" + user.getId(), jsonData);
					} else {
						Response response = new Response(key, request.getUrl(), HttpStatus.FORBIDDEN,
								null,
								null, null, "Fail");

						String jsonResponse = TokenHelper.gson.toJson(response);
						editTaskService.sendMessageToTopic(jsonResponse, "login-response-serv");
					}

					jedis.hdel("editTaskConsumer", key);
					dataMap.remove(key);
				}
			}
		}
	}

}
