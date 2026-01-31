package com.example.ServiceManagement.service;

import com.example.ServiceManagement.dto.Chat;
import com.example.ServiceManagement.dto.CursorChat;
import com.example.ServiceManagement.dto.CursorPage;
import com.example.ServiceManagement.model.ChatMessage;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
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

    public CursorChat<ChatMessage> getChatHistory(Long cursor,LocalDateTime cursorCreatedAt, int limit, Long ticketId){
        Pageable pageable = PageRequest.of(0,limit);

        List<ChatMessage> messages = chatRepo.findNextMessages(ticketId,cursorCreatedAt,cursor,pageable);
        LocalDateTime nextCreatedAt = null;
        Long nextId = null;

        if (!messages.isEmpty()) {
            ChatMessage last = messages.get(messages.size() - 1);
            nextCreatedAt = last.getCreatedAt();
            nextId = last.getId();
        }

        return new CursorChat<>(
                messages,
                nextCreatedAt,
                nextId,
                messages.size() == limit
        );
    }
}
