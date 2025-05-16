package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for generating, validating, and extracting information from JWT tokens.
 */
@Component
public class JwtUtil {

    @Value("${issuer.name}")
    private String ISSUER_NAME;

    @Value("${secret}")
    private String secretKeyString;

    /**
     * Generates a JWT token for the given username.
     */
    public String generateToken(String userName) {
        long EXPIRATION_TIME = 1000 * 60 * 60;
        return Jwts.builder()
                .setSubject(userName)
                .setIssuer(ISSUER_NAME)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKeyString(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username (subject) from the token.
     */
    public String extractUserNameFromToken(String token) {

        return extractClaims(token).getSubject();
    }

    /**
     * Extracts the issuer from the token.
     */
    public String extractIssuerFromToken(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSecretKeyString())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getIssuer(); // this is the 'iss' claim
    }

    /**
     * Extracts claims from the token.
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKeyString())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks whether the token is internally issued by the application.
     */
    public boolean isInternalJwt(String token) {
        try {
            String issuer = extractIssuerFromToken(token);
            return issuer.contains(ISSUER_NAME);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the JWT token from the request's Authorization header.
     */
    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(ApplicationConstants.AUTH_HEADER);
        return (authHeader != null && authHeader.startsWith(ApplicationConstants.BEARER_PREFIX)) ?
                authHeader.substring(7) : null;
    }

    /**
     * Validates the token by checking username match and expiration.
     */
    public Boolean validateToken(String userName, UserDetails userDetails, String token) {
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if the token is expired.
     */
    private boolean isTokenExpired(String token) {

        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Converts the secret string into a SecretKey object.
     */
    private SecretKey getSecretKeyString() {
        if (StringUtils.isEmpty(secretKeyString)) {
            return null;
        }
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }
}
