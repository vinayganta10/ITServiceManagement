package com.example.ServiceManagement.service;

import com.example.ServiceManagement.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatRepo chatRepo;
}
