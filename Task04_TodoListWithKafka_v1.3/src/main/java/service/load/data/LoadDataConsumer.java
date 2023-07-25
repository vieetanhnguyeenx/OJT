package service.load.data;

import com.google.gson.Gson;
import model.Request;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class LoadDataConsumer extends Thread {
    @Override
    public void run() {
        Gson gson = new Gson();

        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "load-data-consumer");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("load-data-serv"));
        LoadDataData.loadDataData.put(1, new Request());
        LoadDataData.loadDataData.remove(1);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(r -> {
                if (!r.value().equalsIgnoreCase("test")) {
                    Request request = gson.fromJson(r.value(), Request.class);
                    System.out.println("request" + request);
                    LoadDataData.loadDataData.put(request.getSocketId(), request);
                }
            });
        }
    }
}
