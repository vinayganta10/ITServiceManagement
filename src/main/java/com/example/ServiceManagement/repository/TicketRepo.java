package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Long> {

    //user
    @Query("SELECT * FROM Ticket WHERE JSON_EXTRACT(raisedBy,$.email)=:email")
    public List<Ticket> findTicketsByUser(String email);

    //user
    @Query("SELECT * FROM Ticket WHERE JSON_EXTRACT(raisedBy,$.email)=:email AND STATUS=':STATUS_FILTER'")
    public List<Ticket> getTicketsByUserStatusFilter(String email,String STATUS_FILTER);

    //agent
    @Query("SELECT * FROM Ticket WHERE JSON_EXTRACT(assignedTo,$.email)=:email")
    List<Ticket> findTicketsByAgent(String email);

    //agent
    @Query("SELECT * FROM Ticket WHERE JSON_EXTRACT(raisedBy,$.email)=:email AND STATUS=':STATUS_FILTER'")
    public List<Ticket> getTicketsByAgentStatusFilter(String email, String STATUS_FILTER);

    //agent
    @Query("UPDATE Ticket SET status=:status where id=:id")
    void updateStatusOfTicket(long id,String status);
}
