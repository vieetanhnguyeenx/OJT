package helper;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;

public class KafkaHelper {
    public static void produceLoginMessage(String url, String method, String header, String payload) {
        String messageValue = "{\"url\":\"" + url + "\",\"method\":\"" + header + "\",\"requestHeader\":\"" + payload + "\",\"payload\":" + payload + " }";
        Properties loginProducer = new Properties();
        loginProducer.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "login-producer");
        loginProducer.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        loginProducer.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        loginProducer.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        try (var producer = new KafkaProducer<String, String>(loginProducer)) {
            final var message = new ProducerRecord<>(
                    "login-request-serv",     //topic name
                    "",            // key
                    messageValue        // value
            );
            producer.send(message);
        }
    }

    public static void consumeLoginMessage(PrintWriter writer, OutputStream outputStream) {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "login-service");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("login-response-serv"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record -> {
                System.out.println(record.value());
            });
        }
    }
}
