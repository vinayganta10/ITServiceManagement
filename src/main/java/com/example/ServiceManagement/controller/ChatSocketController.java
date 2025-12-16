package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.Chat;
import com.example.ServiceManagement.model.ChatMessage;
import com.example.ServiceManagement.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ticket/{ticketId}/send")
    public void receiveMessage(@DestinationVariable Long ticketId,@Payload Chat message){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setTicketId(ticketId);
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        simpMessagingTemplate.convertAndSend("/topic/ticket"+ticketId,message);
    }

}
