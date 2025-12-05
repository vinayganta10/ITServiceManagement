package com.example.ServiceManagement.service;

import com.example.ServiceManagement.model.Agent;
import com.example.ServiceManagement.repository.AgentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
    @Autowired
    AgentRepo agentRepo;

    public Agent getAgentByEmail(String email){
        return agentRepo.findByEmail(email);
    }

    public Agent getAgentByDomainWithMinTickets(String domain){
        return agentRepo.findTopByDomainOrderByNumberOfTicketsAscIdAsc(domain);
    }

    public void getAgentByIdAndUpdateNoOfTickets(long id){
        agentRepo.getAgentByIdAndUpdateNoOfTickets(id);
    }
}
