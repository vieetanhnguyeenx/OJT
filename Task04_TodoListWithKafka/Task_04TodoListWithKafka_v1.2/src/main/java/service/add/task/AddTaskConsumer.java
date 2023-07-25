package service.add.task;

import com.google.gson.Gson;
import model.Request;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import service.edit.task.EditTaskData;

import java.util.Arrays;
import java.util.Properties;

public class AddTaskConsumer extends Thread {
    @Override
    public void run() {
        Gson gson = new Gson();

        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "add-task-consumer");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("add-task-serv"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(r -> {
                Request request = gson.fromJson(r.value(), Request.class);
                System.out.println("request" + request);
                AddTaskData.data.put(request.getSocketId(), request);
            });
        }
    }
}
