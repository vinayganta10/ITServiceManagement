package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.TicketData;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TicketController {

    @Autowired
    TicketService service;

    @GetMapping("/getTicket/{id}")
    public ResponseEntity<Ticket> getTicketByUserId(@PathVariable long id){
        return new ResponseEntity<>(service.getTicketById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllTickets")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return new ResponseEntity<>(service.getAllTickets(),HttpStatus.OK);
    }

    @PostMapping("/addTicket")
    public ResponseEntity<?> addTicket(@RequestBody TicketData ticketData){
        service.addTicket(ticketData);
        return new ResponseEntity<>("Ticket added",HttpStatus.ACCEPTED);
    }

    @GetMapping("/getTicketsByUser")
    public ResponseEntity<List<Ticket>> getTicketsByUser(){
        return new ResponseEntity<>(service.getTicketsByUser(),HttpStatus.OK);
    }
}
