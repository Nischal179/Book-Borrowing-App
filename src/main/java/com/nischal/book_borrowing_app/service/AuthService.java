package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.entity.LibraryUser;
import com.nischal.book_borrowing_app.repository.LibraryUserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private LibraryUserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public LibraryUser register(LibraryUser users) {
        users.setPassword(encoder.encode(users.getPassword()));
        return (userRepository.save(users));
    }
    public void verify(LibraryUser users, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users
                .getUsername(), users.getPassword()));

        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateToken(users.getUsername());

            addCookiesToResponse(response, accessToken);
        }
    }

    private void addCookiesToResponse(HttpServletResponse response, String accessToken) {
        Cookie accessTokenCookie = new Cookie("access_token", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // Set this to true if using HTTPS
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60 * 24); // 24 hrs before cookie expires

        response.addCookie(accessTokenCookie);
    }
}
