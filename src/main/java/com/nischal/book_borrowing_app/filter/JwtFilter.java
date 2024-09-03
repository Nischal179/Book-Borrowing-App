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
            String refreshToken = getTokenFromCookies(request, "refresh_token");

            logger.info("Access Token: " + accessToken);
            logger.info("Refresh Token: " + refreshToken);

            try {
                if (accessToken != null && accessToken.contains(".")) {
                    // Check if the access token is expired
                    if (jwtService.isTokenExpired(accessToken)) {
                        logger.info("Access token expired.");
                        handleExpiredAccessToken(accessToken, refreshToken, response, request);
                    } else {
                        logger.info("Access token is still valid.");
                        authenticateWithToken(accessToken, request);
                    }
                } else {
                    logger.warn("Access token is null or malformed.");
                }
            } catch (ExpiredJwtException e) {
                logger.error("Access token expired and cannot be parsed.");
                handleExpiredAccessToken(e.getClaims().getSubject(), refreshToken, response, request);
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

    private void handleExpiredAccessToken(String username, String refreshToken, HttpServletResponse response,
                                          HttpServletRequest request) {
        if (refreshToken != null && jwtService.validateRefreshToken(username, refreshToken)) {
            logger.info("Refresh token is valid. Generating new access token.");
            String newAccessToken = jwtService.generateToken(username);
            logger.info("New Access Token: " + newAccessToken);

            addCookieToResponse(newAccessToken, response);
            authenticateWithToken(newAccessToken, request);
        } else {
            logger.warn("Invalid or expired refresh token");
        }
    }

    private void addCookieToResponse(String value, HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token",value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 24 hours (1 day)
        response.addCookie(cookie);
    }
}
