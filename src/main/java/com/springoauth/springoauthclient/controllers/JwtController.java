package com.springoauth.springoauthclient.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class JwtController {

    @GetMapping("/jwt/test")
    public String testJwt() {
        return "JWT Content";
    }
}
