package com.example.Task06_RealTimeChat.repository;

import com.example.Task06_RealTimeChat.model.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
    @Query(nativeQuery = true, value = "SELECT [user_id]\n" +
            "  FROM [RealTimeChat].[dbo].[user_room]\n" +
            "  WHERE [room_id] = ?1 AND [user_id] != ?2")
    List<Integer> getAllUserIdInRoom(int roomId, int userId);
}
