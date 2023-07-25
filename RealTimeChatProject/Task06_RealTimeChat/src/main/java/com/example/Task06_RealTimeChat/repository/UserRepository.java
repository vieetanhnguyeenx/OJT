package com.example.Task06_RealTimeChat.repository;

import com.example.Task06_RealTimeChat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);

    @Query(nativeQuery = true, value = "select * from muser where name = ?1 and [user_id] = ?2")
    User findByUserNameAndUserId(String username, int userId);
}
