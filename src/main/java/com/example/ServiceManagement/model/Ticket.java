package com.example.ServiceManagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String description;
    private String domain;
    @ManyToOne
    private User raisedBy;
    @ManyToOne
    private User assignedTo;
    private String Status;
    private Date dateOfCreation;
    private Date dateOfLatestUpdate;
    private Date closedDate;
}
