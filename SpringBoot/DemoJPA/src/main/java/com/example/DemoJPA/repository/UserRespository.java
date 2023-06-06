package com.example.DemoJPA.repository;

import com.example.DemoJPA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
    List<User> findAllByAtk(int atk);
}
