package com.springoauth.springoauthclient.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@Controller
//@RequestMapping("/")
//@Slf4j
//public class IndexController {
//
//    @GetMapping
//    public String index(Model model, Authentication user) {
//        log.info("GET /: user={}", user);
//        model.addAttribute("user", user);
//        return "index";
//    }
//}
