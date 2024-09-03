package com.nischal.book_borrowing_app.filter;

import com.nischal.book_borrowing_app.service.JwtService;
import com.nischal.book_borrowing_app.service.UserDetailsServiceImp;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get access token from cookies
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String accessToken = getTokenFromCookies(request, "access_token");

            logger.info("Access Token: " + accessToken);

            try {
                if (accessToken != null && accessToken.contains(".")) {
                    // Check if the access token is expired
                    if (jwtService.isTokenExpired(accessToken)) {
                        logger.info("Access token expired.");
                    } else {
                        logger.info("Access token is still valid.");
                        authenticateWithToken(accessToken, request);
                    }
                } else {
                    logger.warn("Access token is null or malformed.");
                }
            } catch (ExpiredJwtException e) {
                logger.error("Access token expired and cannot be parsed.");
            }
        }
        filterChain.doFilter(request,response);
    }

    private String getTokenFromCookies(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void authenticateWithToken(String accessToken, HttpServletRequest request) {
        String username = jwtService.extractUserName(accessToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);
            if (jwtService.validateToken(accessToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }
}
