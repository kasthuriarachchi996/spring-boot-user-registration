package com.learn.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY="25442A472D4B6150645367566B59703373367638792F423F4528482B4D625165";
    public String extractUsername(String token) {
        return (String) extractClaim(token, Claims::getSubject);
    }

    public <T> Object extractClaim(String token , Function<Claims, Object> claimsResolver)
    {
        final Claims claims =extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generatatetoken(UserDetails userDetails)
    {
        return generatatetoken(new HashMap<>() , userDetails);
    }

    public String generatatetoken(
            Map<String,Object> extraClaims ,
            UserDetails userDetails
    )
    {
        return Jwts
                .builder().
                setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSigninKey() , SignatureAlgorithm.ES256)
                .compact();
    }

    public Boolean isTokenValid(String token , UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return(username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return (Date) extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder().
                setSigningKey(getSigninKey()).
                build().parseClaimsJws(token).
                getBody();

    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
