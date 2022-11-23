package com.paging.paging.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class SecureController {
    @GetMapping
    public String secured(Authentication authentication) {
        return "Principal name -> " + authentication.getName() + ", authorities -> " + authentication.getAuthorities();
    }
}