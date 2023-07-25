package com.nva.RealTimeMessenger_v22.repository;

import com.nva.RealTimeMessenger_v22.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT [r].[room_id]\n" +
            "      ,[room_name]\n" +
            "      ,[room_type]\n" +
            "      ,[latest_update_date]\n" +
            "      ,[user_1]\n" +
            "      ,[user_2]\n" +
            "      ,[room_image]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[room] r\n" +
            "  JOIN [RealTimeChatVer2].[dbo].[user_room] ur\n" +
            "  ON r.[room_id] = ur.room_id\n" +
            "  WHERE ur.[user_id] = ?1", nativeQuery = true)
    List<Room> findByUserId(int userId);

//    @Query(value = "UPDATE [dbo].[room]\n" +
//            "   SET [latest_update_date] = ?1\n" +
//            " WHERE [room_id] = ?2", nativeQuery = true)
//    void updateLatestUpdateDate(String date, int id);

    @Query(value = "SELECT [room_id]\n" +
            "      ,[room_name]\n" +
            "      ,[room_type]\n" +
            "      ,[latest_update_date]\n" +
            "      ,[user_1]\n" +
            "      ,[user_2]\n" +
            "      ,[room_image]\n" +
            "  FROM [dbo].[room]\n" +
            "  WHERE [room_id] = ?1", nativeQuery = true)
    Room findByRoom_id(int roomId);


}
