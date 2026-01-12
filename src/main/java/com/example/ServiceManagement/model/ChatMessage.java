package com.example.ServiceManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long ticketId;
    private String message;
    private Long senderId;
    private String senderName;
    @ManyToOne
    private User RaisedBy;
    @ManyToOne
    private Agent AssignedTo;
    private LocalDateTime createdAt;
}
