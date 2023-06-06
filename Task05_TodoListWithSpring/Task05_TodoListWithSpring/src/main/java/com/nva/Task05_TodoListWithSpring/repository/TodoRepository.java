package com.nva.Task05_TodoListWithSpring.repository;

import com.nva.Task05_TodoListWithSpring.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
