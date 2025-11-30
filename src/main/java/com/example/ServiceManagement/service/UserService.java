package com.example.ServiceManagement.service;

import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.repository.UserRepo;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public void addUser(User user){
        userRepo.save(user);
    }

    public User getUser(long id){
        return userRepo.findById(id).orElse(new User());
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
