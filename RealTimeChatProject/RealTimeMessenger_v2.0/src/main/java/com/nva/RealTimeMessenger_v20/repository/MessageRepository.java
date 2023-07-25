package com.nva.RealTimeMessenger_v20.repository;

import com.nva.RealTimeMessenger_v20.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
