package com.nva.RealTimeMessenger_v21;

import com.google.gson.Gson;
import com.nva.RealTimeMessenger_v21.dto.UserDto;
import com.nva.RealTimeMessenger_v21.entity.Message;
import com.nva.RealTimeMessenger_v21.entity.Room;
import com.nva.RealTimeMessenger_v21.entity.User;
import com.nva.RealTimeMessenger_v21.entity.UserRoom;
import com.nva.RealTimeMessenger_v21.repository.MessageRepository;
import com.nva.RealTimeMessenger_v21.repository.RoomRepository;
import com.nva.RealTimeMessenger_v21.repository.UserRepository;
import com.nva.RealTimeMessenger_v21.repository.UserRoomRepository;
import com.nva.RealTimeMessenger_v21.util.MessageThread;
import com.nva.RealTimeMessenger_v21.util.Thread01;
import com.nva.RealTimeMessenger_v21.util.Thread02;
import com.nva.RealTimeMessenger_v21.util.TokenHelper;
import org.modelmapper.TypeToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.modelmapper.ModelMapper;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Application.class, args);
        MessageRepository messageRepository = context.getBean(MessageRepository.class);

        ModelMapper modelMapper = context.getBean(ModelMapper.class);

        Jedis jedis = context.getBean(Jedis.class);
//
//		System.out.println(jedis.hexists("relatedUserCache", 1 +""));

//		List<Message> list = messageRepository.findAll();
//		list.forEach(System.out::println);
        Gson gson = context.getBean(Gson.class);
//
        RoomRepository roomRepository = context.getBean(RoomRepository.class);
//		List<Room> roomList = roomRepository.findAll();
//		roomList.forEach(System.out::println);

//		String s = gson.toJson(list);
//		List<Message> messages = gson.fromJson(s, new TypeToken<List<Message>>(){}.getType());
//		Message message123 = messages.get(0);
//		message123.setMessageId(-1);
//		messageRepository.save(message123);

        UserRepository userRepository = context.getBean(UserRepository.class);
//
//		List<Integer> integers = userRepository.findRelatedUsersId(1);
//		integers.forEach(System.out::println);
//
//		List<User> users = userRepository.findByUserId(integers);
//		users.forEach(System.out::println);
//
//		Type listType = new TypeToken<List<UserDto>>(){}.getType();
//		List<UserDto> postDtoList = modelMapper.map(users,listType);
//
//		postDtoList.forEach(System.out::println);
        UserRoomRepository userRoomRepository = context.getBean(UserRoomRepository.class);
//		List<Integer> userRooms = userRoomRepository.findRoomIdByUserId(1);
//		userRooms.forEach(System.out::println);
//		Date date = new Date();
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
//		System.out.println(dateFormat.format(date));
//		Message message = new Message(-1, "hey. anyone here? Good thing the internet exists and provides me answers.", new Date(), 1, 2);
        //messageRepository.save(message);


//		Date date = new Date();
//		String json = gson.toJson(date);
//		String stringDate = json.substring(1, json.length() - 1);
//		System.out.println(stringDate);
//		roomRepository.updateLatestUpdateDate(stringDate, 3);
//
//		Random random = new Random();
//		String lorem = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.";
//
//
//		long aDay = TimeUnit.DAYS.toMillis(1);
//		long now = new Date().getTime();
//		Date start = new Date(now - aDay * 365 * 7);
//		Date end = new Date(now - aDay * 365);
//
//		List<User> users = userRepository.findAll();
//		List<UserDto> userDtoList = modelMapper.map(users, new TypeToken<List<UserDto>>(){}.getType());
//		Map<Integer, String> tokenList = new HashMap<>();
//		userDtoList.forEach((userDto) -> {
//			tokenList.put(userDto.getUserId(), TokenHelper.encoder.encodeToString(gson.toJson(userDto).getBytes()));
//		});
//		String s = gson.toJson(tokenList);
//		System.out.println(s);
//		Map<Integer, String> faf = gson.fromJson(s, new TypeToken<Map<Integer, String>>(){}.getType());
//		for (Map.Entry<Integer, String> map: faf.entrySet()) {
//			System.out.println(map.getValue());
//		}

//		int roomId = 9;
//		Date date = new Date();
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
//		System.out.println(dateFormat.format(date));

//		int corePoolSize = 5;
//		int maximumPoolSize = 100;
//		long keepAliveTime = 500;
//		TimeUnit unit = TimeUnit.SECONDS;
//
//		ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
//
//		RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
//
//		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
//				maximumPoolSize, keepAliveTime, unit, workQueue, handler);
//		for (int i = 0; i < 10; i++) {
//			threadPoolExecutor.execute(new MessageThread(tokenList, date, i + 10, roomId));
//		}

    }

    public static Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}
