package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.repository.LibraryUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Autowired
    private LibraryUserRepository userRepository;

    // Inject the secret key from the application properties
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    @Value("${jwt.refreshExpiration}")
    private int refreshExpirationInMs;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String generateToken(String username) {
        return generateToken(username, jwtExpirationInMs);
    }

    // Method to generate refresh token
    public String generateToken(String username, int expirationTimeInMs) {

        logger.info("Generating token for users: " + username);
        Map<String, Object> claims = new HashMap<>();

        // Building the JWT with the claims
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTimeInMs))
                .and()
                .signWith(getKey())
                .compact();
    }

    // Method to sign the JWT for data signature
    private SecretKey getKey() {
        // Using base64 decoder to convert your string to byte
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String extractUserName(String token) {
        // extract the username from the jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Method to validate JWT
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Check if the JWT is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Method to extract expiration from JWT
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
