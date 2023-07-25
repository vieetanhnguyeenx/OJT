package com.nva.RealTimeMessenger_v20.repository;

import com.nva.RealTimeMessenger_v20.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
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
}
