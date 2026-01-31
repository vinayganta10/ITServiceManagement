package com.example.ServiceManagement.service;

import com.example.ServiceManagement.dto.CursorPage;
import com.example.ServiceManagement.dto.TicketData;
import com.example.ServiceManagement.model.Agent;
import com.example.ServiceManagement.model.Ticket;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

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
    public CursorPage<Ticket> getAllTickets(Long cursor, int limit){
        Pageable pageable = PageRequest.of(0, limit);

        List<Ticket> tickets = ticketRepo.findNextPage(cursor, pageable);

        boolean hasMore = tickets.size() == limit;
        Long nextCursor = hasMore ? tickets.get(tickets.size() - 1).getId() : null;
        return new CursorPage<>(tickets, nextCursor, hasMore);
    }

    //user
    public long addTicket(TicketData ticketData){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser();
        Agent agent = agentService.getAgentByDomainWithMinTickets(ticketData.getDomain());
        Ticket ticket = Ticket.builder().subject(ticketData.getSubject())
                .description(ticketData.getDescription())
                .domain(ticketData.getDomain())
                .raisedBy(user)
                .assignedTo(agent)
                .dateOfLatestUpdate(LocalDateTime.now())
                .closedDate(null)
                .build();
        ticketRepo.save(ticket);
        agentService.getAgentByIdAndUpdateNoOfTickets(agent.getId());
        return ticket.getId();
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
    public void updateStatusOfTicket(long id,long agentId,String status) throws AccessDeniedException{
        Ticket ticket = ticketRepo.findById(id).orElse(new Ticket());
        if(agentId!=ticket.getAssignedTo().getId()){
            throw new AccessDeniedException("You are not authorized to update this ticket");
        }
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

    //user
    public long cloneTicket(long id){
        Ticket existingTicket = ticketRepo.findById(id).orElse(new Ticket());
        Ticket newTicket = existingTicket.copy();
        newTicket.setRaisedBy(userService.getUser());
        newTicket.setAssignedTo(agentService.getAgentByDomainWithMinTickets(existingTicket.getDomain()));
        newTicket.setDateOfCreation(LocalDateTime.now());
        newTicket.setDateOfLatestUpdate(LocalDateTime.now());
        ticketRepo.save(newTicket);
        agentService.getAgentByIdAndUpdateNoOfTickets(newTicket.getAssignedTo().getId());
        return newTicket.getId();
    }

    //ETag hash
    public String generateETagForAllTickets(){
        Object[] meta = (Object[]) ticketRepo.fetchTicketListMeta();
       LocalDateTime ldt = (LocalDateTime) meta[0];
       Instant instant = ldt.atZone(ZoneOffset.UTC).toInstant();
       int count = ((Long) meta[1]).intValue();
       String eTag = "\"tickets-" + instant.toEpochMilli() + "-" + count + "\"";
        System.out.println(eTag);
       return eTag;
    }
}
