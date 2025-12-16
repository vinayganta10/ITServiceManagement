package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.model.ChatMessage;
import com.example.ServiceManagement.service.ChatService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ChatRestController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable long ticketId){
        return new ResponseEntity<>(chatService.getChatHistory(ticketId), HttpStatus.OK);
    }

}
