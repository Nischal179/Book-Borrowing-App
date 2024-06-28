package com.nischal.book_borrowing_app.controller;

import com.nischal.book_borrowing_app.dto.AuthRequest;
import com.nischal.book_borrowing_app.dto.AuthResponse;
import com.nischal.book_borrowing_app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
        String jwtToken = jwtUtil.generateToken(authRequest.getUsername());
        return new AuthResponse("Login successful", jwtToken);
    }
}