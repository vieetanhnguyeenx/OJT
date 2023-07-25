package org.example;

import com.google.gson.Gson;
import helper.HTTPServerHelper;
import helper.JsonHelper;
import model.LoginResponse;
import model.TestModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import model.LoginRequest;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            List<String> header = null;

            InputStream inputStream = null;
            OutputStream outputStream = null;
            BufferedReader bufferedReader = null;
            PrintWriter writer = null;

            while (true) {
                // Connect and print log
                Socket socket = serverSocket.accept();
                System.out.println("[Server_log]: Connected" + LocalDateTime.now());

                // Get Reader
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                writer = new PrintWriter(outputStream, true);
                header = new ArrayList<>();

                // Get Header request
                String hederLine = null;
                while ((hederLine = bufferedReader.readLine()).length() != 0) {
                    System.out.println(hederLine);
                    header.add(hederLine);
                }

                // Get payload for doPost()
                StringBuilder payload = new StringBuilder();
                while (bufferedReader.ready()) {
                    payload.append((char) bufferedReader.read());
                }
                // Get method, url, param string
                String method = HTTPServerHelper.getMethod(header.get(0));
                String url = HTTPServerHelper.getUrl(header.get(0));

                if (url.equalsIgnoreCase("/login") || url.equalsIgnoreCase("/")) {
                    LoginRequest loginRequest = new LoginRequest(url, method, header.get(0), payload.toString());
//                    produceLogin(loginRequest);
//                    consumeLogin(writer, outputStream);
//                    Gson gson = new Gson();
//                    TestModel testModel = new TestModel(inputStream, outputStream, writer);
//                    String test = gson.toJson(testModel, TestModel.class);
//                    TestModel testModel1 = gson.fromJson(test, TestModel.class);
//                    HTTPServerHelper.forwardHtml(testModel1.getPrintWriter(), testModel1.getOutputStream(), "login.html");
                }
            }

        } catch (IOException e) {
            System.out.println();
        }

    }

    private static void produceLogin(LoginRequest loginRequest) {
        Gson gson = new Gson();
        String json = gson.toJson(loginRequest);
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "login-request-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, String.valueOf(10 * 1024 * 1024L));

        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)) {
            producer.send(new ProducerRecord<>("login-request-serv", json));
        }

    }

    private static void consumeLogin(PrintWriter writer, OutputStream outputStream) {
        Gson gson = new Gson();
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "login-consumer");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("login-response-serv"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(r -> {
                LoginResponse response = gson.fromJson(r.value(), LoginResponse.class);
                System.out.println(response);
                if (response != null) {
                    if (response.getStatusCode().equalsIgnoreCase(LoginResponse.SC_OK)) {
                        try {
                            HTTPServerHelper.forwardHtml(writer, outputStream, response.getHtmlResponse());
                        } catch (IOException e) {
                            System.out.println("Error");
                        }
                    }
                }
            });
            break;
        }
    }


}