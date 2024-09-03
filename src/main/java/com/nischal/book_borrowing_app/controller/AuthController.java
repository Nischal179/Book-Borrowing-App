package com.nischal.book_borrowing_app.controller;


import com.nischal.book_borrowing_app.entity.LibraryUser;
import com.nischal.book_borrowing_app.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LibraryUser users, HttpServletResponse response) {
        authService.verify(users, response);
        return ResponseEntity.ok("User logged in successfully");
    }
}