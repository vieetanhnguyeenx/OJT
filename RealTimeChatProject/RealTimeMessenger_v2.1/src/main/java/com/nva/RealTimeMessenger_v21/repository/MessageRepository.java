package com.nva.RealTimeMessenger_v21.repository;

import com.nva.RealTimeMessenger_v21.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "SELECT [message_id]\n" +
            "      ,[message]\n" +
            "      ,[created_date_time]\n" +
            "      ,[room_id]\n" +
            "      ,[user_id]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[message]\n" +
            "  WHERE [room_id] = ?1", nativeQuery = true)
    List<Message> getMessageByRoomId(int roomId);

    @Query(value = "SELECT * FROM (SELECT [message_id]\n" +
            "      ,[message]\n" +
            "      ,[created_date_time]\n" +
            "      ,[room_id]\n" +
            "      ,[user_id]\n" +
            "  FROM [dbo].[message] \n" +
            "  WHERE [room_id] = ?1 AND created_date_time < ?2\n" +
            "  ORDER BY created_date_time DESC\n" +
            "  OFFSET ?3 ROW\n" +
            "  FETCH FIRST 5 ROW ONLY) AS [d]\n" +
            "  ORDER BY [d].[created_date_time]", nativeQuery = true)
    List<Message> getMessageWithPaging(int roomId, Date date, int offsetIndex);


}
