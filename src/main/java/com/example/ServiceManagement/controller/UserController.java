package com.example.ServiceManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

class Ticket{
    int id;
    String name;
    public Ticket(int id,String name){
        this.id=id;
        this.name=name;
    }
}

@RestController
public class UserController {
    List<Ticket> tickets = new ArrayList<>();
    @GetMapping
    public List<Ticket> getTickets(){
        return tickets;
    }

    @PostMapping
    public void addTicket(Ticket ticket){
        tickets.add(new Ticket(ticket.id,ticket.name));
        System.out.println("Ticket successfully added");
    }

    @PatchMapping
    public void updateTicket(Ticket ticket){

    }

}
