package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket,Integer> {
}
