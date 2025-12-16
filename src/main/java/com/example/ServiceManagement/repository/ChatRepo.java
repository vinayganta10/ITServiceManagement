package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepo extends JpaRepository<ChatMessage,Long> {
    public List<ChatMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}
