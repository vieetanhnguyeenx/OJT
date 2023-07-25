package org.example;

import helper.HTTPServerHelper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final String TOPIC_LOGIN_REQ = "login_request_serv";
    private static final String TOPIC_LOGIN_RES = "login_response_serv";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "group-id-main";

    public static void main(String[] args) {
        Properties propertiesConsumer = new Properties();
        propertiesConsumer.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        propertiesConsumer.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        propertiesConsumer.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        propertiesConsumer.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Properties propertiesProducer = new Properties();
        propertiesProducer.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        propertiesProducer.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        propertiesProducer.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        propertiesProducer.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        try {
            List<String> header = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            BufferedReader bufferedReader = null;
            PrintWriter writer = null;
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("[Server_Log] Connected");

            while (true) {
                // Connect and print log
                Socket socket = serverSocket.accept();
                System.out.println("[Server_log]: Connected");

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


                    try (var producer = new KafkaProducer<String, String>(propertiesProducer)) {
                        final var message = new ProducerRecord<>(
                                TOPIC_LOGIN_REQ,     //topic name
                                "",            // key
                                "hello mtfk"        // value
                        );
                        producer.send(message);
                    }
                }


                inputStream.close();
                outputStream.close();
                bufferedReader.close();
                writer.close();
                socket.close();
                System.out.println("[Server_Log]: Closed");
            }


        } catch (IOException e) {
            System.out.println("[Server_Log] Fail to connect");
        }
    }


}