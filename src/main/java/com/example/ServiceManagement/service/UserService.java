package com.example.ServiceManagement.service;

import com.example.ServiceManagement.dto.CursorPage;
import com.example.ServiceManagement.dto.SignupRequest;
import com.example.ServiceManagement.exceptions.BusinessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import com.example.ServiceManagement.dto.LoginRequest;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.repository.UserRepo;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    RestTemplate restTemplate;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void addUser(SignupRequest signupRequest){
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setType(signupRequest.getType().name());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setJoinedAt(LocalDateTime.now());
        userRepo.save(user);
    }

    public String verify(LoginRequest loginRequest){
        Authentication authentication;

        try {
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            // Wrong email or password
            throw new BusinessException("Invalid email or password");
        }

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BusinessException("Invalid email or password"));

        String role = user.getType();

        if (!authentication.isAuthenticated()) {
            throw new BusinessException("Invalid email or password");
        }
        HttpEntity<Map<String,Object>> entity =  emailRequestBuilder(user);

        restTemplate.exchange("http://localhost:8082/v1/emails/send",
                HttpMethod.POST,
                entity,
                String.class);

        return jwtService.generateToken(user.getEmail(), role);
    }

    public User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email).orElseThrow(() -> new BusinessException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new BusinessException("User not found"));
    }

    public CursorPage<User> getAllUsers(Long cursor,int limit){
        Pageable pageable = PageRequest.of(0, limit);

        List<User> users = userRepo.findNextPage(cursor,pageable);

        boolean hasMore = users.size() == limit;
        Long nextCursor = hasMore ? users.get(users.size() - 1).getId() : null;
        return new CursorPage<>(users, nextCursor, hasMore);
    }

    public HttpEntity<Map<String,Object>> emailRequestBuilder(User user){
        Map<String, Object> request = new HashMap<>();
        request.put("to",List.of(user.getEmail()));
        request.put("cc",null);
        request.put("subject","User Login alert");
        request.put("template","user-login");
        Map<String,Object> data = new HashMap<>();
        data.put("username",user.getName());
        data.put("time",LocalDateTime.now());
        request.put("data",data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(request, headers);
    }
}
