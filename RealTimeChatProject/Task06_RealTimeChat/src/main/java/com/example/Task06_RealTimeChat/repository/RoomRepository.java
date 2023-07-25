package com.example.Task06_RealTimeChat.repository;

import com.example.Task06_RealTimeChat.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "select r.room_id, r.room_name, r.room_type, r.latest_update, r.room_image, r.user_1, r.user_2 from Room r join user_room ur on ur.room_id = r.room_id where ur.user_id = ?1", nativeQuery = true)
    List<Room> findByUserId(int userId);
}
