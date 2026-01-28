package com.example.ServiceManagement.config;

import com.example.ServiceManagement.exceptions.BusinessException;
import com.example.ServiceManagement.model.User;
import com.example.ServiceManagement.repository.UserRepo;
import com.example.ServiceManagement.service.JwtService;
import com.example.ServiceManagement.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token =
                (OAuth2AuthenticationToken) authentication;

        OAuth2User oauthUser = token.getPrincipal();

        String email = oauthUser.getAttribute("email");
        User user = userRepo.findByEmail(email).orElseThrow(() -> new BusinessException("User not found"));
        String name = user.getName();
        String role = user.getType();
        String jwt = jwtService.generateToken(email,role);

        response.sendRedirect(
                "http://localhost:3000/oauth-success?token=" + jwt+"&name="+name+"&role="+role
        );
    }
}
