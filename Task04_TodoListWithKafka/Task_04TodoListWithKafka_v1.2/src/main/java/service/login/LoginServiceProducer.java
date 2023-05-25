package service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import context.UserDAO;
import core.TokenUserCache;
import helper.HTTPServerHelper;
import helper.JsonHelper;
import model.Request;
import model.Response;
import model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Base64;
import java.util.Map;
import java.util.Properties;

public class LoginServiceProducer extends Thread {

    @Override
    public void run() {
        Properties producerProperties = new Properties();
        Gson gson = new Gson();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "login-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        while (true) {
            if(LoginData.data.size() > 0) {
                for (Map.Entry<Integer, Request> data : LoginData.data.entrySet()) {
                    Integer key = data.getKey();
                    Request val = data.getValue();
                    if (val.getMethod().equalsIgnoreCase("get")) {
                        Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_OK, null, "login.html", null, null);
                        String json = gson.toJson(response);
                        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
                            producer.send(new ProducerRecord<>("login-response-serv", json));
                            producer.flush();
                        }
                    } else if (val.getMethod().equalsIgnoreCase("post")) {
                        String username = HTTPServerHelper.getParameter(val.getPayload(), "username");
                        String password = HTTPServerHelper.getParameter(val.getPayload(), "password");
                        User user = UserDAO.getUser(username, password);
                        if (user != null) {
                            JsonNode jsonNode = JsonHelper.toJson(user);
                            String jString = null;
                            try {
                                jString = JsonHelper.stringgify(jsonNode);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            Base64.Encoder encoder = Base64.getEncoder();
                            String token = encoder.encodeToString(jString.getBytes());
                            TokenUserCache.tokenCache.put(user.getId(), token);
                            Response response = new Response(key, val.getUrl(), val.getMethod(), Response.SC_FOUND, "http://localhost:8888/todolist?token" + "=" + token, null, null, null);
                            JsonNode jsonNode1 = JsonHelper.toJson(response);
                            String jsonResponse = null;
                            try {
                                jsonResponse = JsonHelper.stringgify(jsonNode1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }

                            System.out.println(jsonResponse);
                            try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
                                producer.send(new ProducerRecord<>("login-response-serv", jsonResponse));
                                producer.flush();
                            }
                        } else {
                            Response response = new Response(key, val.getUrl(), val.getMethod() ,Response.SC_FOUND, "http://localhost:8888/login", null, null, null);
                            String json = gson.toJson(response);
                            System.out.println(json);
                            try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
                                producer.send(new ProducerRecord<>("login-response-serv", json));
                                producer.flush();
                            }
                        }
                    }
                    LoginData.data.remove(key);
                }
            }
        }
    }
}
