//package com.nischal.book_borrowing_app.controller;
//
//import com.nischal.book_borrowing_app.dto.AuthRequest;
//import com.nischal.book_borrowing_app.dto.AuthResponse;
//import com.nischal.book_borrowing_app.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
//        if ("admin".equals(authRequest.getUsername()) && "admin".equals(authRequest.getPassword())) {
//            String token = JwtUtil.generateToken(authRequest.getUsername());
//            return ResponseEntity.ok(new AuthResponse("Login successful", token));
//        }
//        String jwtToken = JwtUtil.generateToken(authRequest.getUsername());
//        return ResponseEntity.status(401).body("Invalid credentials");
//    }
//}