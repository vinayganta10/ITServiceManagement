package com.example.ServiceManagement.controller;


import com.example.ServiceManagement.dto.LoginRequest;
import com.example.ServiceManagement.model.Agent;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.service.AgentService;
import com.example.ServiceManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AgentService agentService;

    @GetMapping("/test")
    public String test(){
        return "Hello world!!!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest){
        String token = userService.verify(loginRequest);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>("User signup successful",HttpStatus.OK);
    }
}
