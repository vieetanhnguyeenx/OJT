package com.example.Task06_RealTimeChat;

import com.example.Task06_RealTimeChat.dto.LoginUserDto;
import com.example.Task06_RealTimeChat.model.Message;
import com.example.Task06_RealTimeChat.model.Room;
import com.example.Task06_RealTimeChat.model.User;
import com.example.Task06_RealTimeChat.model.UserRoom;
import com.example.Task06_RealTimeChat.repository.MessageRepository;
import com.example.Task06_RealTimeChat.repository.RoomRepository;
import com.example.Task06_RealTimeChat.repository.UserRepository;
import com.example.Task06_RealTimeChat.repository.UserRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Task06RealTimeChatApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Task06RealTimeChatApplication.class, args);
        UserRepository userRepository = context.getBean(UserRepository.class);

//		ModelMapper modelMapper = context.getBean(ModelMapper.class);
////		User user = userRepository.findByUserNameAndUserId("anhnv", 1);
////		System.out.println(user);
//		RoomRepository roomRepository = context.getBean(RoomRepository.class);
//		List<Room> list = roomRepository.findByUserId(1);
//		list.forEach(System.out::println);
//////		List<User> users = userRepository.findAll();
//////		users.forEach(System.out::println);
////
//		MessageRepository messageRepository = context.getBean(MessageRepository.class);
//		List<Message> messages = messageRepository.findByRoomId(1);
//		messages.forEach(System.out::println);

//		User u = new User(-1, "doiden", "12345", "@doiden", null);
//		userRepository.save(u);

//		List<LoginUserDto> list = new ArrayList<>();
//		List<User> users = userRepository.findAll();
//		Long t1 = System.currentTimeMillis();
//		users.forEach(u -> {
//			list.add(modelMapper.map(u, LoginUserDto.class));
//			System.out.println(u);
//		});
//		Long t2 = System.currentTimeMillis();
//		System.out.println(t2 - t1);

        UserRoomRepository userRoomRepository = context.getBean(UserRoomRepository.class);
        List<Integer> userRooms = userRoomRepository.getAllUserIdInRoom(1, 1);
        userRooms.forEach(System.out::println);

    }

}
