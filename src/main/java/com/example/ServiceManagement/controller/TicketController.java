package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.CursorPage;
import com.example.ServiceManagement.dto.TicketData;
import com.example.ServiceManagement.dto.TicketStatusUpdate;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<CursorPage<Ticket>> getAllTickets
            (@RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch,
             @RequestParam(defaultValue = "4") int limit,
             @RequestParam(required = false) Long cursor){
        String eTag = ticketService.generateETagForAllTickets();
        if(eTag.equals(ifNoneMatch)){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return ResponseEntity.ok()
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(ticketService.getAllTickets(cursor,limit));
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
