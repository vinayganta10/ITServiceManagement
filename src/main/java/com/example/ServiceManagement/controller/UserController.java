package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.CursorPage;
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

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(){
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<CursorPage<User>> getAllUsers(@RequestParam(required = false) Long cursor,
                                                        @RequestParam(defaultValue = "5") int limit){
        return new ResponseEntity<>(userService.getAllUsers(cursor,limit), HttpStatus.OK);
    }
}
