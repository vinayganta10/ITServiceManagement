package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<ChatMessage,Long> {
}
