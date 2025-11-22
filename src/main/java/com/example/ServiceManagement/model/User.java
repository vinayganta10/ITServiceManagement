package com.example.ServiceManagement.model;

import jakarta.persistence.Entity;

@Entity
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String type;
}
