package com.nva.RealTimeMessenger_v22.repository;

import com.nva.RealTimeMessenger_v22.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
    @Query(value = "SELECT [user_id]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[user_room]\n" +
            "  WHERE [room_id] = ?1 AND [user_id] != ?2", nativeQuery = true)
    List<Integer> getUserRoomByRoomId(int roomId, int userId);

    @Query(value = "SELECT [room_id]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[user_room]\n" +
            "  WHERE [user_id] = ?1", nativeQuery = true)
    List<Integer> findRoomIdByUserId(int userId);

    @Query(value = "SELECT [id]\n" +
            "      ,[is_admin]\n" +
            "      ,[user_id]\n" +
            "      ,[room_id]\n" +
            "      ,[alias_name]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[user_room]\n" +
            "  WHERE [room_id] = ?1", nativeQuery = true)
    List<UserRoom> getUserRoomByRoomId(int roomId);
}
