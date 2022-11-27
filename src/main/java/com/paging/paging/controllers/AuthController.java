package com.paging.paging.controllers;

import com.paging.paging.model.LoginParam;
import com.paging.paging.services.JwtTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/authenticate", "/login"})
@Slf4j
public class AuthController {
    private final AuthenticationManager authManager;

    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthController(JwtTokenGenerator jwtTokenGenerator, AuthenticationManager authManager) {
        this.authManager = authManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping(consumes = "application/json")
    public String generateToken(@RequestBody LoginParam loginParam) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginParam.username(), loginParam.password()));
        return jwtTokenGenerator.generateJWTTokens(authentication);
    }
}