package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    // User
    @Query("SELECT t FROM Ticket t WHERE t.raisedBy.email = :email")
    List<Ticket> findTicketsByUser(@Param("email") String email);

    // User with Status Filter
    @Query("SELECT t FROM Ticket t WHERE t.raisedBy.email = :email AND t.status = :status")
    List<Ticket> getTicketsByUserStatusFilter(@Param("email") String email, @Param("status") String status);

    // Agent
    @Query("SELECT t FROM Ticket t WHERE t.assignedTo.email = :email")
    List<Ticket> findTicketsByAgent(@Param("email") String email);

    // Agent with Status Filter
    @Query("SELECT t FROM Ticket t WHERE t.assignedTo.email = :email AND t.status = :status")
    List<Ticket> getTicketsByAgentStatusFilter(@Param("email") String email, @Param("status") String status);

    // Update Ticket Status
    @Transactional
    @Modifying
    @Query("UPDATE Ticket t SET t.status = :status WHERE t.id = :id")
    void updateStatusOfTicket(@Param("id") long id, @Param("status") String status);

    @Query("SELECT MAX(t.dateOfLatestUpdate), COUNT(t) FROM Ticket t")
    Object fetchTicketListMeta();

    @Query("""
        SELECT t
        FROM Ticket t
        WHERE (:cursor IS NULL OR t.id < :cursor)
        ORDER BY t.id DESC
    """)
    List<Ticket> findNextPage(
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}

