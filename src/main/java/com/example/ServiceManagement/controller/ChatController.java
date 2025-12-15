package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/app/message")
    public void receiveMessage(@Payload ChatMessage message){

    }

}
