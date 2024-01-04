package com.springoauth.springoauthclient.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    private SecretKey jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();

        // Credentials (usually a password or token)
        Object credentials = authentication.getCredentials();


        // Authorities/Roles

        String subject = principal.getName();
        Date issuedAt = new Date();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();



        // Extract all attributes as claims
        Map<String, Object> attributes = principal.getAttributes();

        jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Generate JWT token
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(attributes)
                .claim("principal",principal.getName())
                .claim("credentials", credentials)
                .claim("authorities", roles)
                .setIssuedAt(issuedAt)
                .setExpiration(expiryDate)
                .signWith(jwtSecret)
                .compact();
    }

    public UserDetails loadUserByJwtToken(String jwtToken) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(jwtToken);

        Claims claims = claimsJws.getBody();

        String username = claims.getSubject();

        // Extract roles/authorities from claims
        List<String> roles = claims.get("authorities", List.class);
        Collection<GrantedAuthority> authorities = mapToGrantedAuthorities(roles);


        return new User(username, "", authorities);
    }

    private Collection<GrantedAuthority> mapToGrantedAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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