package com.nva.RealTimeMessenger_v21.util;

import com.nva.RealTimeMessenger_v21.Application;
import com.nva.RealTimeMessenger_v21.entity.Message;
import com.nva.RealTimeMessenger_v21.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Thread02 implements Runnable {
    @Autowired
    private MessageRepository messageRepository;

    long aDay = TimeUnit.DAYS.toMillis(1);
    long now = new Date().getTime();
    Date start = new Date(now - aDay * 365 * 7);
    Date end = new Date(now - aDay * 365);

    String lorem = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.";

    @Override
    public void run() {
        List<Message> messageList = new ArrayList<>();
        for (int i = 1; i < 1000000; i++) {
            Message message = new Message();
            message.setMessage(lorem);
            message.setCreatedDate(Application.between(start, end));
            message.setRoomId(9);
            message.setUserId(Application.getRandomNumberUsingNextInt(10, 1113));
            messageList.add(message);
        }
        messageRepository.saveAll(messageList);
        System.out.println("done");
    }
}
