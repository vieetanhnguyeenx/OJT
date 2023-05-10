package helper;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class KafkaHelper {
    public static void produceLoginMessage(String url, String method, String header, String payload){
        String messageValue = "{\n" +
                "  \"url\": " + url + ",\n" +
                "  \"method\": " + method + ",\n" +
                "  \"requestHeader\": " + header + ",\n" +
                "  \"payload\": " + payload + "\n" +
                "}";
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

    }
}
