package com.springoauth.springoauthclient.controllers;

import com.springoauth.springoauthclient.model.dao.OrganizationRepository;
import com.springoauth.springoauthclient.model.entity.Foo;
import com.springoauth.springoauthclient.model.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @PreAuthorize("hasPermission(#id, 'Foo', 'write')")
    @GetMapping("/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id) {
        return new Foo("Sample");
    }

    @PreAuthorize("hasPermission(#foo, 'write')")
    @PostMapping("/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo create(@RequestBody final Foo foo) {
        return foo;
    }

    @PreAuthorize("hasAuthority('FOO_READ_PRIVILEGE')")
    @GetMapping("/foos")
    @ResponseBody
    public Foo findFooByName(@RequestParam final String name) {
        return new Foo(name);
    }

    @PreAuthorize("isMember(#id)")
    @GetMapping("/organizations/{id}")
    @ResponseBody
    public Organization findOrgById(@PathVariable final String id) {
        return organizationRepository.findById(1L)
                .orElse(null);
    }

}