package com.nva.Task05_TodoListWithSpring_LoadData.repository;

import com.nva.Task05_TodoListWithSpring_LoadData.model.Todo;
import com.nva.Task05_TodoListWithSpring_LoadData.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Query(value = "SELECT * FROM task03_httpserver.task where UserID = ?1", nativeQuery = true)
    List<Todo> findByUserId(int userId);
}
