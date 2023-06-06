package com.example.Task05_TodoListWithSpringBoot;

import com.example.Task05_TodoListWithSpringBoot.repository.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class Task05TodoListWithSpringBootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Task05TodoListWithSpringBootApplication.class, args);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);

        todoRepository.findAll().forEach(System.out::println);
    }

}
