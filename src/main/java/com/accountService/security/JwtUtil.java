package com.accountService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Utility class for handling JWT (JSON Web Token) operations including token validation
 * and extraction of claims.
 */
@Service
public class JwtUtil {

    private final String SECRET_KEY = "your-secure-secret-key-change-this"; // Use a secure and shared key

    /**
     * Extracts the username from the JWT token.
     * @param token The JWT token string
     * @return The username stored in the token's subject claim
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extracts the user roles from the JWT token.
     * @param token The JWT token string
     * @return A list of role strings stored in the token's roles claim
     */
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return (List<String>) extractClaims(token).get("roles", List.class);
    }

    /**
     * Validates whether a JWT token is valid and not expired.
     * @param token The JWT token string to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Helper method to extract all claims from a JWT token.
     * @param token The JWT token string
     * @return Claims object containing all the claims from the token
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
