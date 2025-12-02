package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepo extends JpaRepository<Agent, Long> {

    @Query("SELECT * FROM AGENT WHERE domain=:domain ORDER BY numberOfTickets,Id LIMIT 1;")
    Agent getAgentByDomainWithMinTickets(String domain);

    @Query("UPDATE AGENT SET numberOfTickets = " +
            "(SELECT COUNT(*) FROM TICKET WHERE JSON_EXTRACT(assignedTo, '$.id') = :id AND (status = 'OPEN' OR status='AWAITING_REPLY'))" +
            "WHERE id = :id;")
    Agent getAgentByIdAndUpdateNoOfTickets(long id);
}
