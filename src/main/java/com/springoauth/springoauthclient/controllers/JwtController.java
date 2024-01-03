package com.springoauth.springoauthclient.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @GetMapping("/jwt/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String testJwt() {
        return "JWT Content";
    }
}
