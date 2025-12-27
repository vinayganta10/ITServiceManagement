package com.example.ServiceManagement.service;

import com.example.ServiceManagement.model.ChatMessage;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private TicketService ticketService;

    public ChatMessage saveMessage(ChatMessage message){
        Ticket ticket = ticketService.getTicketById(message.getTicketId());
        message.setRaisedBy(ticket.getRaisedBy());
        message.setAssignedTo(ticket.getAssignedTo());
        message.setCreatedAt(LocalDateTime.now());
        return chatRepo.save(message);
    }

    public List<ChatMessage> getChatHistory(Long ticketId){
        return chatRepo.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}
