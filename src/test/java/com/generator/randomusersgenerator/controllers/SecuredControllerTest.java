package com.generator.randomusersgenerator.controllers;

import com.generator.randomusersgenerator.security.SecurityChain;
import com.generator.randomusersgenerator.services.JwtTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecuredController.class})
@Import({JwtTokenGenerator.class, SecurityChain.class, AuthController.class})
class SecuredControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void whenUnauthorized_then401() throws Exception {
        this.mockMvc.perform(get("/secured"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails
    void whenAuthenticatedUser_then200() throws Exception {
        this.mockMvc.perform(get("/secured"))
                .andExpect(status().isOk())
                .andExpect(content().string("Principal name -> user, authorities -> [read]"))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = "admin")
    void whenAuthenticatedAdmin_then200() throws Exception {
        this.mockMvc.perform(get("/secured"))
                .andExpect(status().isOk())
                .andExpect(content().string("Principal name -> admin, authorities -> [create, delete, read]"))
                .andReturn();
    }
}