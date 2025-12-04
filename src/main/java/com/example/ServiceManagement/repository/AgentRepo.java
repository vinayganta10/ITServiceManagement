package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AgentRepo extends JpaRepository<Agent, Long> {

    Agent findTopByDomainOrderByNumberOfTicketsAscIdAsc(String domain);


    @Transactional
    @Modifying
    @Query("""
       UPDATE Agent a SET a.numberOfTickets = (
           SELECT COUNT(t) FROM Ticket t
           WHERE t.assignedTo.id = :id
             AND (t.status = 'OPEN' OR t.status = 'AWAITING_REPLY')
       )
       WHERE a.id = :id
       """)
    Agent getAgentByIdAndUpdateNoOfTickets(@Param("id") long id);
}
