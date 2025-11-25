package com.example.ServiceManagement.service;

import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;

    public Ticket getTicketById(long id){
        return ticketRepo.findById(id).orElse(new Ticket());
    }

    public List<Ticket> getAllTickets(){
        return ticketRepo.findAll();
    }

    public void addTicket(Ticket ticket){
        ticketRepo.save(ticket);
    }

//    public List<Ticket> getTicketsByUserID(long id){
//        return ticketRepo.getTicketsByUserId(id);
//    }
//
//    public List<Ticket> getTicketsByStatusFilter(String filter){
//        return ticketRepo.getTicketsByStatusFilter(filter);
//    }
//
//    public List<Ticket> getTicketsByDomainFilter(String domain){
//        return ticketRepo.getTicketsByDomainFilter(domain);
//    }


}
