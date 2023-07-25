package com.nva.RealTimeMessenger_v22.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nva.RealTimeMessenger_v22.cache.TokenCache;
import com.nva.RealTimeMessenger_v22.dto.UserDto;
import com.nva.RealTimeMessenger_v22.entity.Message;
import com.nva.RealTimeMessenger_v22.entity.Room;
import com.nva.RealTimeMessenger_v22.entity.User;
import com.nva.RealTimeMessenger_v22.repository.MessageRepository;
import com.nva.RealTimeMessenger_v22.repository.RoomRepository;
import com.nva.RealTimeMessenger_v22.repository.UserRepository;
import com.nva.RealTimeMessenger_v22.util.TokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ChatService implements IService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private Gson gson;

    private final Jedis jedis = new Jedis();
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto authentication(String token) {
        try {
            if (TokenCache.tokenCache.containsKey(token)) {
                System.out.println("TokenCache");
                return TokenCache.tokenCache.get(token);
            } else {
                UserDto userDto = gson.fromJson(new String(TokenHelper.decoder.decode(token)), UserDto.class);
                User user = userRepository.findByUserIdAndUsername(userDto.getUserId(), userDto.getUsername());
                if (user != null) {
                    return userDto;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ResponseEntity<?> getMessageFormRoom(String token, int roomId) {
        try {
            UserDto userDto = authentication(token);
            if (userDto != null) {
                List<Message> list = messageRepository.getMessageByRoomId(roomId);
                System.out.println("[getMessageFormRoom] Return from database");
                return ResponseEntity.ok().body(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("getMessageFormRoom(): Return null");
        return ResponseEntity.badRequest().body(null);
    }


    public void resetChatCache(int roomId) {
        jedis.hdel("messageCache", String.valueOf(roomId));
        List<Message> list = messageRepository.getMessageByRoomId(roomId);
        jedis.hset("messageCache", String.valueOf(roomId) + "", gson.toJson(list));
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
        Room room = roomRepository.findByRoom_id(message.getRoomId());
        room.setLatestUpdateDate(message.getCreatedDate());
        roomRepository.save(room);
        jedis.del("roomCache");
        List<Room> list = roomRepository.findByUserId(message.getUserId());
        jedis.hset("roomCache", message.getUserId() + "", gson.toJson(list));
    }

    public ResponseEntity<?> getMessageFormRoomWithPaging(String token, int roomId, int page, String date) {
        try {
            UserDto userDto = authentication(token);
            if (userDto != null) {
                int offsetIndex = (page - 1) * 5;
                Date dateTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS")
                        .parse(date);
                List<Message> list = messageRepository.getMessageWithPaging(roomId, dateTemp, offsetIndex);
                list.forEach(System.out::println);
                System.out.println("[getMessageFormRoom] Return from database");
                return ResponseEntity.ok().body(list);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("getMessageFormRoom(): Return null");
        return ResponseEntity.badRequest().body(null);
    }
}
