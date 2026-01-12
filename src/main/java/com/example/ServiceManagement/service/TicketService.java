package com.example.ServiceManagement.service;

import com.example.ServiceManagement.dto.TicketData;
import com.example.ServiceManagement.model.Agent;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    UserService userService;

    @Autowired
    AgentService agentService;

    //all
    public Ticket getTicketById(long id){
        System.out.println(id);
        return ticketRepo.findById(id).orElse(new Ticket());
    }

    //admin
    public List<Ticket> getAllTickets(){
        return ticketRepo.findAll();
    }

    //user
    public void addTicket(TicketData ticketData){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser();
        Agent agent = agentService.getAgentByDomainWithMinTickets(ticketData.getDomain());
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
        agentService.getAgentByIdAndUpdateNoOfTickets(agent.getId());
    }

    //agent
    public List<Ticket> getTicketsByAgent(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ticketRepo.findTicketsByAgent(email);
    }

    //agent
    public List<Ticket> getTicketsByAgentStatusFilter(String filter){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ticketRepo.getTicketsByAgentStatusFilter(email,filter);
    }

    //agent
    public void updateStatusOfTicket(long id,long agentId,String status){
        ticketRepo.updateStatusOfTicket(id,status);
        if(status.equalsIgnoreCase("closed")){
            agentService.getAgentByIdAndUpdateNoOfTickets(agentId);
        }
    }

    //user
    public List<Ticket> getTicketsByUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ticketRepo.findTicketsByUser(email);
    }

    //user
    public List<Ticket> getTicketsByUserStatusFilter(String filter){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ticketRepo.getTicketsByUserStatusFilter(email,filter);
    }
}
