package com.example.ServiceManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ServiceManagement.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
