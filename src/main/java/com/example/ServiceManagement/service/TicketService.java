package com.example.ServiceManagement.service;

import com.example.ServiceManagement.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;

}
