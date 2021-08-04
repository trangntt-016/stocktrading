package com.canada.edu.stocktrading.security;


import com.canada.edu.stocktrading.service.impl.UserEntityServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@NoArgsConstructor
public class TokenProvider {
    @Autowired
    UserEntityServiceImpl userService;


    @Value("${secretKey}")
    private String JWT_SECRET;

    private static final String EMAIL = "email";
    private static final String USER_ID = "userId";


    public <T> T extractClaim(String token, Function<Claims,T>claimsResolver){
        // extract all claims
        Claims claims = extractAllClaims(token);
        // extract a single claim from claims
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public String createToken(String email, String userId) {
        return  Jwts.builder()
                .claim(EMAIL,email)
                .claim(USER_ID,userId)
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET)
                .compact();
    }

}