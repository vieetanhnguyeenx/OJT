package com.example.Task06_RealTimeChat.repository;

import com.example.Task06_RealTimeChat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "/****** Script for SelectTopNRows command from SSMS  ******/\n" +
            "SELECT [message_id]\n" +
            "      ,[message]\n" +
            "      ,[created_date]\n" +
            "      ,[room_id]\n" +
            "      ,[user_id]\n" +
            "  FROM [RealTimeChat].[dbo].[Message]\n" +
            "  WHERE room_id = ?1\n" +
            "  ORDER BY created_date ", nativeQuery = true)
    List<Message> findByRoomId(int roomId);

}
