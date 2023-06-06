package com.example.Task05_TodoListWithSpringBoot.repository;

import com.example.Task05_TodoListWithSpringBoot.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
