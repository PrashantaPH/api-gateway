package com.api.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.function.Function;

@Component
public class JwtUtil {

   public void validateToken(String token) {
       Jwts.parserBuilder().setSigningKey(getSignedKey()).build().parseClaimsJws(token);
   }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public  <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignedKey() {
        byte[] keyBytes = Decoders.BASE64.decode("6DdeonFf4OyTTSzS76nSzmlOjvusBkCjvtgRB0lcvR4");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
