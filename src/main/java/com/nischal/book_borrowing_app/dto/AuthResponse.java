package com.nischal.book_borrowing_app.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String message;
    private String jwtToken;

    public AuthResponse(String message) {

        this.message = message;
    }
}
