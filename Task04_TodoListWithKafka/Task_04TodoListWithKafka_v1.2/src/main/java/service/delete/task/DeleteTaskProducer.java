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
import service.edit.task.EditTaskData;

import java.util.Map;
import java.util.Properties;

public class DeleteTaskProducer extends Thread{
    @Override
    public void run() {
        Properties producerProperties = new Properties();
        Gson gson = new Gson();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "delete-task-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        while (true) {
            if (DeleteTaskData.data.size() > 0) {
                for (Map.Entry<Integer, Request> data : DeleteTaskData.data.entrySet()) {
                    Integer key = data.getKey();
                    Request val = data.getValue();
                    System.out.println(val);

                    if (val.getMethod().equalsIgnoreCase("post")) {
                        String token = HTTPServerHelper.getParameter(val.getPayload(), "token");
                        User u1 = null;
                        boolean isAuthenticated = false;
                        if (TokenUserCache.tokenCache.containsValue(token)) {
                            isAuthenticated = true;
                            u1 = TokenHelper.tokenToUser(token);
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
                                u1 = u;
                            }
                        }

                        if (isAuthenticated) {
                            int taskId = Integer.parseInt(HTTPServerHelper.getParameter(val.getPayload(), "id"));
                            TodoDAO.deleteTask(u1.getId(), taskId);
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, null, null, token);
                            response.setTextData("Delete Success");
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
                        DeleteTaskData.data.remove(key);
                    }
                }
            }
        }
    }
}
