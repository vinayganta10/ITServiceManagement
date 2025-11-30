package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.service.TicketService;
import com.example.ServiceManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TicketController {

    @Autowired
    TicketService service;

    @Autowired
    UserService userService;

    @GetMapping("/getTicket/{id}")
    public ResponseEntity<Ticket> getTicketByUserId(@PathVariable long id){
        return new ResponseEntity<>(service.getTicketById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllTickets")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return new ResponseEntity<>(service.getAllTickets(),HttpStatus.OK);
    }

    @PostMapping("/addTicket")
    public ResponseEntity<?> addTicket(@RequestBody Ticket ticket){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        ticket.setRaisedBy(user);
        service.addTicket(ticket);
        return new ResponseEntity<>("Ticket added",HttpStatus.ACCEPTED);
    }
}
