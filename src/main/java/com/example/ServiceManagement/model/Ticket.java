package com.example.ServiceManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    private String description;
    private String domain;
    //private User raisedBy;
    //private User assignedTo;
    private String Status;
    private Date dateOfCreation;
    private Date dateOfLatestUpdate;
    private Date closedDate;
}
