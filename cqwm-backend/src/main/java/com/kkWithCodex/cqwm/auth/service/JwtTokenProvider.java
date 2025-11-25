package com.kkWithCodex.cqwm.auth.service;

import com.kkWithCodex.cqwm.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(Long userId, String username, boolean rememberMe) {
        long expirySeconds = rememberMe ? jwtProperties.getExpiration() * 7 : jwtProperties.getExpiration();
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(expirySeconds);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .claim("username", username)
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getExpiration(boolean rememberMe) {
        return rememberMe ? jwtProperties.getExpiration() * 7 : jwtProperties.getExpiration();
    }

    private Key signingKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
