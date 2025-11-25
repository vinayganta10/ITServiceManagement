package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable long id){
        return userService.getUser(id);
    }

    @PostMapping("/addUser")
    public void addUser(User user){
        userService.addUser(user);
    }
}
