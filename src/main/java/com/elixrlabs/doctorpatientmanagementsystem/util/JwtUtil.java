package com.elixrlabs.doctorpatientmanagementsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${secret}")
    private String secretKeyString;

    public String generateToken(String userName) {
        long EXPIRATION_TIME = 1000 * 60 * 60;
        return Jwts.builder()
                .setSubject(userName)
                .setIssuer("elixr")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKeyString(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserNameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractIssuerFromToken(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSecretKeyString())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getIssuer(); // this is the 'iss' claim
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKeyString())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean validateToken(String userName, UserDetails userDetails, String token) {
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private SecretKey getSecretKeyString() {
        if (StringUtils.isEmpty(secretKeyString)) {
            return null;
        }
           return Keys.hmacShaKeyFor(secretKeyString.getBytes());

    }
}