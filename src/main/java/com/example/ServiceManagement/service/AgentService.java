package com.example.ServiceManagement.service;

import com.example.ServiceManagement.model.Agent;
import com.example.ServiceManagement.repository.AgentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
    @Autowired
    AgentRepo agentRepo;

    public Agent getAgentByDomainWithMinTickets(String domain){
        return agentRepo.getAgentByDomainWithMinTickets(domain);
    }

    public Agent getAgentByIdAndUpdateNoOfTickets(long id){
        return agentRepo.getAgentByIdAndUpdateNoOfTickets(id);
    }
}
