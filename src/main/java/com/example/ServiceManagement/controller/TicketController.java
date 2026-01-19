package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.TicketData;
import com.example.ServiceManagement.dto.TicketStatusUpdate;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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
        long id = ticketService.addTicket(ticketData);
        return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getTicketsByUser")
    public ResponseEntity<List<Ticket>> getTicketsByUser(){
        return new ResponseEntity<>(ticketService.getTicketsByUser(),HttpStatus.OK);
    }

    @PostMapping("/updateStatusOfTicket")
    public ResponseEntity<?> updateStatusOfTicket(@RequestBody TicketStatusUpdate ticketStatusUpdate ) throws AccessDeniedException {
        long id = ticketStatusUpdate.getId();
        long agentId = ticketStatusUpdate.getAgentId();
        String status = ticketStatusUpdate.getStatus();
        ticketService.updateStatusOfTicket(id,agentId,status);
        return new ResponseEntity<>("Ticket is moved to "+status+" status",HttpStatus.OK);
    }

    @PostMapping("/cloneTicket")
    public ResponseEntity<?> cloneTicket(@RequestBody Map<String,Long> requestBody){
        long id = requestBody.get("id");
        long newTicketId = ticketService.cloneTicket(id);
        return new ResponseEntity<>(newTicketId,HttpStatus.ACCEPTED);
    }
}
