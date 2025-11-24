package com.example.ServiceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ServiceManagement.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
}
