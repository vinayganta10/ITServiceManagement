package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.model.ChatMessage;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.service.ChatService;
import com.example.ServiceManagement.service.UserService;
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

    @Autowired
    private UserService userService;

    @MessageMapping("/ticket/{ticketId}/send")
    public void receiveMessage(@DestinationVariable Long ticketId,@Payload ChatMessage message){
        message.setTicketId(ticketId);
        User sender = userService.getUser();
        message.setSenderName(sender.getName());
        message.setSenderId(sender.getId());
        ChatMessage savedMessage = chatService.saveMessage(message);
        simpMessagingTemplate.convertAndSend("/ticket/" + ticketId, savedMessage);
    }

}
