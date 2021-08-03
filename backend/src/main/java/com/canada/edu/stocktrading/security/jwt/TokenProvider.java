package com.canada.edu.stocktrading.security.jwt;


import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.service.impl.UserEntityServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;


@Service
@NoArgsConstructor
public class TokenProvider {
    @Autowired
    UserEntityServiceImpl userService;


    @Value("${secretKey}")
    private String JWT_SECRET;

    public static final int JWT_EXPIRATION_REMEMBER = 1209600000; //14 days
    public static final int JWT_EXPIRATION_WITHOUT_REMEMBER = 300000; //5 mins
    private static final String EMAIL = "email";
    private static final String USER_ID = "userId";

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).get(EMAIL).toString();
    }

    public <T> T extractClaim(String token, Function<Claims,T>claimsResolver){
        // extract all claims
        Claims claims = extractAllClaims(token);
        // extract a single claim from claims
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public boolean isValidated(String authToken) {
        // true if extracted expiry Date is after today
        return extractExpiration(authToken).after(new Date());
    }

    public String createToken(String email, String userId) {
        return  Jwts.builder()
                .claim(EMAIL,email)
                .claim(USER_ID,userId)
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET)
                .compact();
    }

    public Authentication getAuthentication(String token){
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));
        User principal = new User(extractUsername(token),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal,token);
    }

    public String resolveToken(HttpServletRequest request){// decrypt token
        String bearerToken = request.getHeader("Authorization");
        if(!Objects.isNull(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}