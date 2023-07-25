package com.nva.RealTimeMessenger_v21.repository;

import com.nva.RealTimeMessenger_v21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT\t[user_id]\n" +
            "      ,[username]\n" +
            "      ,[password]\n" +
            "      ,[avartar]\n" +
            "      ,[identify_code]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[c_user]\n" +
            "  WHERE [username] = ?1 AND [password] = ?2", nativeQuery = true)
    User findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT [user_id]\n" +
            "      ,[username]\n" +
            "      ,[password]\n" +
            "      ,[avartar]\n" +
            "      ,[identify_code]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[c_user]\n" +
            "  WHERE [user_id] = ?1 AND [username] = ?2", nativeQuery = true)
    User findByUserIdAndUsername(int userId, String Username);

    @Query(value = "SELECT DISTINCT [user_id] FROM (\n" +
            "\tSELECT ur.[user_id] FROM [room] r\n" +
            "\tJOIN [user_room] ur\n" +
            "\tOn r.room_id = ur.room_id \n" +
            "\tWHERE r.room_id IN (\n" +
            "\t\tSELECT r.room_id FROM [room] r\n" +
            "\t\tJOIN [user_room] ur\n" +
            "\t\tOn r.room_id = ur.room_id \n" +
            "\t\tWHERE ur.[user_id] = ?1)\n" +
            ") as us\n" +
            "WHERE [user_id] != ?1", nativeQuery = true)
    List<Integer> findRelatedUsersId(int userId);

    @Query(value = "SELECT [user_id]\n" +
            "      ,[username]\n" +
            "      ,[password]\n" +
            "      ,[avartar]\n" +
            "      ,[identify_code]\n" +
            "  FROM [RealTimeChatVer2].[dbo].[c_user]\n" +
            "  WHERE [user_id] IN :ids", nativeQuery = true)
    List<User> findByUserId(@Param("ids") List<Integer> userId);
}
