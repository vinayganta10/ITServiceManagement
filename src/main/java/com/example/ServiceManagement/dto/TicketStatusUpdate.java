package com.example.ServiceManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatusUpdate {
    private Long id;
    private Long agentId;
    private String status;
}
