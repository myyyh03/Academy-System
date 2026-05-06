package com.mo7s.academySystem.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SIGNIN_KEY = "your_secret_key_here";

    public String extractUsername(String token){
            return extractClaim(token , Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }


    public String generateToken(Object userDetails){
        return generateToken(new HashMap<>() , userDetails);
    }

    public String generateToken(Map<String , Object> extraClaims , Object userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSign() , SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSign() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNIN_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
         Claims claims = extractAllClaims(token);
         return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSign())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token , Object userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.toString())) && !isTokenExpired(token);
    }
}
