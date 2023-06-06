package com.nva.Task05_TodoListWithSpring_Edit.repository;

import com.nva.Task05_TodoListWithSpring_Edit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
}
