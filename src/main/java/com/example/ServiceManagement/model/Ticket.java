package com.example.ServiceManagement.model;

import com.example.ServiceManagement.prototypes.Copy;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket implements Copy<Ticket> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String description;
    private String domain;
    @ManyToOne
    private User raisedBy;
    @ManyToOne
    private Agent assignedTo;
    @Builder.Default
    private String status = "OPEN";

    @Builder.Default
    private LocalDateTime dateOfCreation = LocalDateTime.now();
    private LocalDateTime dateOfLatestUpdate;
    private LocalDateTime closedDate;

    @Override
    public Ticket copy() {
        Ticket ticket = Ticket.builder().
                subject(this.subject).
                description(this.description).
                domain(this.domain).
                assignedTo(this.assignedTo).build();
        return ticket;
    }
}
