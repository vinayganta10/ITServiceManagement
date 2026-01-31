package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.CursorChat;
import com.example.ServiceManagement.model.ChatMessage;
import com.example.ServiceManagement.service.ChatService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ChatRestController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<CursorChat<ChatMessage>> getChatHistory
            (@PathVariable long ticketId,@RequestParam(defaultValue = "10") int limit,
             @RequestParam(required = false) Long cursor,
             @RequestParam(required = false) LocalDateTime cursorCreatedAt){
        return new ResponseEntity<>(chatService.getChatHistory(cursor,cursorCreatedAt,limit,ticketId), HttpStatus.OK);
    }

}
