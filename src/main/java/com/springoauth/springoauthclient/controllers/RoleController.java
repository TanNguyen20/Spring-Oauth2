package com.springoauth.springoauthclient.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @GetMapping("private-info")
    @PreAuthorize("hasRole('TANNGUYEN_ADMIN')")
    public String getPrivateInfo() {
        return "private info";
    }
}
