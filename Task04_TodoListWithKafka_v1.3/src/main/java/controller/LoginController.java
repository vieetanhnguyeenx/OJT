package controller;

import com.google.gson.Gson;
import model.Request;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class LoginController implements BaseController {

    @Override
    public void sendMessage(int socketId, String method, String url, String header, String payload) {
        Gson gson = new Gson();
        Request request = new Request(socketId, url, method, header, payload);
        String json = gson.toJson(request);
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "login-request-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("login-request-serv", json));
        }
    }
}
