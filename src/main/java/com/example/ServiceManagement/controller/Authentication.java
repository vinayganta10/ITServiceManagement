package com.example.ServiceManagement.controller;


import com.example.ServiceManagement.dto.LoginRequest;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Authentication {

    @Autowired
    UserService userService;
    @GetMapping("/test")
    public String test(){
        return "Hello world!!!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest){
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if(user.getId()==null) return new ResponseEntity<>("Email is incorrect",HttpStatus.NOT_FOUND);
        if(user.getPassword().equals(loginRequest.getPassword())){
            return new ResponseEntity<>("Login successful",HttpStatus.OK);
        }
        else return new ResponseEntity<>("Password is incorrect",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>("User signup successful",HttpStatus.OK);
    }
}
