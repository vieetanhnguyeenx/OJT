package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.HTTPServerHelper;
import helper.JsonHelper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Properties;

public class LoginService {
    private static final String CRLF = "\n\r";

    public static void main(String[] args) {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "login-service");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("login-request-serv"));

        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "java-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String message = record.value();
                System.out.println(message +LocalDateTime.now());
                try {
                    System.out.println(JsonHelper.parse(message.toString()).findValue("url").asText());
                } catch (JsonProcessingException e) {
                    System.out.println("Error ");
                }
            }
        }

    }
    private static void doGet(Properties properties, String topic, String record) {
        try {
            String htmlRespone = HTTPServerHelper.getHtml("login.html");
            String respone = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Type: text/html;charset=utf-8" + CRLF +
                    "Content-Length: " + htmlRespone.getBytes(StandardCharsets.UTF_8).length + CRLF +
                    htmlRespone.getBytes(StandardCharsets.UTF_8) + CRLF + CRLF;
            String method = JsonHelper.parse(record.toString()).findValue("method").asText();
            String url = JsonHelper.parse(record.toString()).findValue("url").asText();
            System.out.println(htmlRespone);
            System.out.println(respone);
            System.out.println(method);
            String json = "{\n" +
                    "  \"url\":  " + url + " ,\n" +
                    "  \"method\": " + method + ",\n" +
                    "  \"htppResponese\": " + respone + ",\n" +
                    "  \"token\": " + null + "\n" +
                    "}";
            try (var producer = new KafkaProducer<String, String>(properties)) {
                final var message = new ProducerRecord<>(
                        "login-response-serv",     //topic name
                        "",            // key
                        json        // value
                );
                producer.send(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
