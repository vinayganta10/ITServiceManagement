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
    private TicketService ticketService;

    @GetMapping("/getTicket/{id}")
    public ResponseEntity<Ticket> getTicketByUserId(@PathVariable long id){
        return new ResponseEntity<>(ticketService.getTicketById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllTickets")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return new ResponseEntity<>(ticketService.getAllTickets(),HttpStatus.OK);
    }

    @PostMapping("/addTicket")
    public ResponseEntity<?> addTicket(@RequestBody TicketData ticketData){
        ticketService.addTicket(ticketData);
        return new ResponseEntity<>("Ticket added",HttpStatus.ACCEPTED);
    }

    @GetMapping("/getTicketsByUser")
    public ResponseEntity<List<Ticket>> getTicketsByUser(){
        return new ResponseEntity<>(ticketService.getTicketsByUser(),HttpStatus.OK);
    }

    @PostMapping("/updateStatusOfTicket")
    public ResponseEntity<?> updateStatusOfTicket(@RequestBody Map<String,Object> data){
        long id = ((Number) data.get("id")).longValue();
        long agentId = ((Number) data.get("agentId")).longValue();
        String status = (String) data.get("status");
        ticketService.updateStatusOfTicket(id,agentId,status);
        return new ResponseEntity<>("Ticket is moved to "+status+" status",HttpStatus.OK);
    }
}
