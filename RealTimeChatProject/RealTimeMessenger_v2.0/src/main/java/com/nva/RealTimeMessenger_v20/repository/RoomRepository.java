package com.nva.RealTimeMessenger_v20.repository;

import com.nva.RealTimeMessenger_v20.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "/****** Script for SelectTopNRows command from SSMS  ******/\n" +
            "SELECT [r].[room_id]\n" +
            "      ,[r].[room_name]\n" +
            "      ,[r].[room_type]\n" +
            "      ,[r].[latest_update_date]\n" +
            "      ,[r].[user_1]\n" +
            "      ,[r].[user_2]\n" +
            "      ,[r].[room_image]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[room] r\n" +
            "  JOIN [RealTimeChatVer2].[dbo].[user_room] ur\n" +
            "  ON r.[room_id] = ur.room_id\n" +
            "  WHERE ur.[user_id] = ?1", nativeQuery = true)
    List<Room> findByUserId(int userId);
}
