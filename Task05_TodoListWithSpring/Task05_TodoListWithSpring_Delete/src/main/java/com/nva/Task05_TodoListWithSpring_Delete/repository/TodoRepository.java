package com.nva.Task05_TodoListWithSpring_Delete.repository;

import com.nva.Task05_TodoListWithSpring_Delete.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Query(value = "SELECT * FROM task03_httpserver.task where UserID = ?1", nativeQuery = true)
    List<Todo> findByUserId(int userId);

    @Query(value = "SELECT * FROM task03_httpserver.task where ID = ?1 and UserID = ?2", nativeQuery = true)
    Todo findByIdAndUserID(int id, int userId);
}
