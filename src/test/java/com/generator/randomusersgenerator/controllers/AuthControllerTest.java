package com.generator.randomusersgenerator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paging.paging.model.LoginParam;
import com.paging.paging.security.SecurityChain;
import com.paging.paging.services.JwtTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class, SecureController.class})
@Import({JwtTokenGenerator.class, SecurityChain.class})
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenUnauthenticatedThen401() throws Exception {
        this.mockMvc
                .perform(get("/secured"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAuthenticatedThen200() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(post("/authenticate")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(new LoginParam("admin", "secret")))
//                        .param("username", "admin")
//                        .param("password", "secret")
                )
                .andExpect(status().isOk())
                .andReturn();
        String token = mvcResult.getResponse().getContentAsString();

        this.mockMvc.perform(get("/secured")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Principal name -> admin, authorities -> " + "[SCOPE_delete, SCOPE_read]"));
    }

    @Test
    @WithUserDetails
    void authenticatedUserThen200() throws Exception {
        this.mockMvc.perform(get("/secured"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithUserDetails(value = "admin")
    void whenAuthenticatedAdminThen200() throws Exception {
        this.mockMvc.perform(get("/secured"))
                .andExpect(status().isOk())
                .andReturn();
    }
}