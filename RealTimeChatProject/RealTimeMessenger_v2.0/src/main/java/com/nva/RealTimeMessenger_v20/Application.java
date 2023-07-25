package com.nva.RealTimeMessenger_v20;


import com.nva.RealTimeMessenger_v20.model.Message;
import com.nva.RealTimeMessenger_v20.model.Room;
import com.nva.RealTimeMessenger_v20.model.User;
import com.nva.RealTimeMessenger_v20.repository.MessageRepository;
import com.nva.RealTimeMessenger_v20.repository.RoomRepository;
import com.nva.RealTimeMessenger_v20.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        UserRepository userRepository = context.getBean(UserRepository.class);
        List<User> list = userRepository.findAll();
        list.forEach(System.out::println);

        MessageRepository messageRepository = context.getBean(MessageRepository.class);
        List<Message> messages = messageRepository.findAll();
        messages.forEach(System.out::println);

        RoomRepository roomRepository = context.getBean(RoomRepository.class);
        List<Room> rooms = roomRepository.findByUserId(1);
        rooms.forEach(System.out::println);


    }

}
