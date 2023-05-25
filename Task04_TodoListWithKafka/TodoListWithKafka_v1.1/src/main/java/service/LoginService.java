package service;

import com.google.gson.Gson;
import helper.HTTPServerHelper;
import model.LoginRequest;
import model.LoginResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

public class LoginService {
    private static final String CRLF = "\n\r";
    public static void main(String[] args) {
        Gson gson = new Gson();

        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "login-consumer");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("login-request-serv"));



        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(r -> {
                LoginRequest request = gson.fromJson(r.value(), LoginRequest.class);
                if (request.getMethod().equalsIgnoreCase("get")) {
                    //doGet(request);
                    System.out.println(request);
                }
            });
        }
    }

    private static void doGet(LoginRequest loginRequest) {
            Properties producerProperties = new Properties();
            producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "login-producer");
            producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            producerProperties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, String.valueOf(10 * 1024 * 1024L));
            LoginResponse loginResponse = new LoginResponse(LoginResponse.SC_OK, null, "login.html", null);
            System.out.println(loginResponse);
            Gson gson = new Gson();
            String json = gson.toJson(loginResponse);
            try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
                producer.send(new ProducerRecord<>("login-response-serv", json));
                producer.flush();
            }
    }
}
