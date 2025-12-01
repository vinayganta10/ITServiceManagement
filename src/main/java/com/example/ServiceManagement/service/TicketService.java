package com.example.ServiceManagement.service;

import com.example.ServiceManagement.dto.TicketData;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    UserService userService;

    public Ticket getTicketById(long id){
        return ticketRepo.findById(id).orElse(new Ticket());
    }

    public List<Ticket> getAllTickets(){
        return ticketRepo.findAll();
    }

    public void addTicket(TicketData ticketData){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        User agent = userService.getUser(2);

        Ticket ticket = new Ticket();
        ticket.setSubject(ticketData.getSubject());
        ticket.setDescription(ticketData.getDescription());
        ticket.setDomain(ticketData.getDomain());
        ticket.setStatus("OPEN");
        ticket.setRaisedBy(user);
        ticket.setAssignedTo(agent);
        ticket.setDateOfCreation(LocalDateTime.now());
        ticket.setDateOfLatestUpdate(LocalDateTime.now());
        ticket.setClosedDate(null);
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
