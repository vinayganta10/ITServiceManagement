package com.example.ServiceManagement.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
public class ApiError {
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ApiError(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}
