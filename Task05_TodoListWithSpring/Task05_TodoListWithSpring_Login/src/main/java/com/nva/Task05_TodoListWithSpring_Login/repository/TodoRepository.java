package com.nva.Task05_TodoListWithSpring_Login.repository;

import com.nva.Task05_TodoListWithSpring_Login.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
