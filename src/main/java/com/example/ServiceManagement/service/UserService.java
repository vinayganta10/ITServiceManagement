package com.example.ServiceManagement.service;


import org.springframework.security.core.Authentication;
import com.example.ServiceManagement.dto.LoginRequest;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.repository.UserRepo;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void addUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));  
        userRepo.save(user);
    }

    public String verify(LoginRequest loginRequest){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(loginRequest.getEmail());
        }
        else return "Invalid user";
    }

    public User getUser(long id){
        return userRepo.findById(id).orElse(new User());
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
