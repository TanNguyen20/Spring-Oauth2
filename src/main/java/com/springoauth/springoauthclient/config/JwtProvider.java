package com.springoauth.springoauthclient.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private SecretKey jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        OAuth2User userPrincipal = (OAuth2User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(userPrincipal.getAttribute("email"))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(jwtSecret)
                .compact();
    }

    public String getUserUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecret).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException ex) {

        }
        return false;
    }
}