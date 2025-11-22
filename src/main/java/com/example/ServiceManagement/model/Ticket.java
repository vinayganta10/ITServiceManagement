package com.example.ServiceManagement.model;

import jakarta.persistence.Entity;
import java.util.Date;

@Entity
public class Ticket {
    private int id;
    private String subject;
    private String description;
    private String domain;
    private User raisedBy;
    private User assignedTo;
    private String Status;
    private Date dateOfCreation;
    private Date dateOfLatestUpdate;
    private Date closedDate;
}
