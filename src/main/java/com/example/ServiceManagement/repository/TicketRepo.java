package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Long> {

//    @Query("SELECT * FROM Ticket WHERE STATUS=':STATUS_FILTER'")
//    public List<Ticket> getTicketsByStatusFilter(String STATUS_FILTER);
//
//    @Query("SELECT * FROM Ticket T,USER U WHERE T.raisedBy=U.:id")
//    public List<Ticket> getTicketsByUserId(long id);
//
//    @Query("SELECT * FROM Ticket WHERE domain=:DOMAIN_FILTER")
//    public List<Ticket> getTicketsByDomainFilter(String DOMAIN_FILTER);
}
