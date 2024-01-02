package com.springoauth.springoauthclient.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final String JWT_SECRET = "tannguyentannguyentannguyentannguyentannguyentannguyentannguyentannguyentannguyentannguyentannguyentannguyen";
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        DefaultOAuth2User userPrincipal = (DefaultOAuth2User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getAttribute("email"))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(JWT_SECRET).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException ex) {

        }
        return false;
    }
}