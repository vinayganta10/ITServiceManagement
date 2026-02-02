package com.example.ServiceManagement.controller;


import com.example.ServiceManagement.dto.LoginRequest;
import com.example.ServiceManagement.dto.LoginResponse;
import com.example.ServiceManagement.dto.SignupRequest;
import com.example.ServiceManagement.model.Agent;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.service.AgentService;
import com.example.ServiceManagement.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest){
        String token = userService.verify(loginRequest);
        User user = userService.getUserByEmail(loginRequest.getEmail());
        LoginResponse response = new LoginResponse();
        response.setName(user.getName());
        response.setRole(user.getType());
        response.setToken(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@Valid @RequestBody SignupRequest signupRequest){
        signupRequest.setEmail(signupRequest.getEmail().toLowerCase());
        signupRequest.setName(signupRequest.getName().replaceAll("\\s",""));
        userService.addUser(signupRequest);
        return new ResponseEntity<>("User signup successful",HttpStatus.OK);
    }
}
