package service.todo.list;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import context.TodoDAO;
import context.UserDAO;
import core.TokenUserCache;
import helper.HTTPServerHelper;
import model.Request;
import model.Response;
import model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;

public class TodoListProducer extends Thread {
    @Override
    public void run() {
        Properties producerProperties = new Properties();
        Gson gson = new Gson();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "todo-producer");
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
        while (true) {
            if (TodoData.todoData.size() > 0) {
                for (Map.Entry<Integer, Request> data : TodoData.todoData.entrySet()) {
                    Integer key = data.getKey();
                    Request val = data.getValue();
                    if (val.getMethod().equalsIgnoreCase("get")) {
                        String parameterString = HTTPServerHelper.getParameterString(val.getRequestHeader());
                        String token = HTTPServerHelper.getParameter(parameterString, "token");
                        boolean isAuthenticated = false;
                        if (TokenUserCache.tokenCache.containsValue(token)) {
                            isAuthenticated = true;
                        } else {
                            User u = null;
                            try {
                                u = UserDAO.authenticationToken(token);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            if (u != null) {
                                System.out.println("not null");
                                isAuthenticated = true;
                                TokenUserCache.tokenCache.put(u.getId(), token);
                            }
                        }

                        if (isAuthenticated) {
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, "todoList.html", null, token);
                            String json = gson.toJson(response);
                            System.out.println(json);
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                        } else {
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, "accessDenied.html", null, null);
                            String json = gson.toJson(response);
                            System.out.println(json);
                            try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                        }

                    }
//                    Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, "todoList.html", null, null);
//                    String json = gson.toJson(response);
//                    System.out.println(json);
//                    try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
//                        producer.send(new ProducerRecord<>("login-response-serv", json));
//                        producer.flush();
//                    }
                    TodoData.todoData.remove(key);
                }
            }
        }
    }
}
