package com.generator.randomusersgenerator.controllers;

import com.generator.randomusersgenerator.model.LoginParam;
import com.generator.randomusersgenerator.services.JwtTokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/generateToken"},consumes = "application/json")
@Slf4j
public class AuthController {
    private final AuthenticationManager authManager;

    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthController(JwtTokenGenerator jwtTokenGenerator, AuthenticationManager authManager) {
        this.authManager = authManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping
    public ResponseEntity<String> generateToken(@RequestBody LoginParam loginParam) {
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginParam.username(),loginParam.password()));
            return ResponseEntity.accepted().body(jwtTokenGenerator.generateJWTTokens(authentication));
        } catch (AuthenticationException authenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}