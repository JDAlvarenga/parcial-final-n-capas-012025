package com.uca.parcialfinalncapas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time}")
    private String expirationTime;

    public String createToken(Authentication authentication)
    {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Long.parseLong(expirationTime));

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getKey())
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValidToken(String token)
    {
        Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parse(token);
        return true;
    }

    private Key getKey()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


}
