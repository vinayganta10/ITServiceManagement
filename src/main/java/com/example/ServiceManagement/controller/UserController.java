package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
}
