package com.generator.randomusersgenerator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generator.randomusersgenerator.model.LoginParam;
import com.generator.randomusersgenerator.security.SecurityChain;
import com.generator.randomusersgenerator.services.JwtTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class})
@Import({JwtTokenGenerator.class, SecurityChain.class})
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


//    @MockBean
    @Autowired
    AuthenticationManager authManager;

    @Test
    void whenUnauthenticated_then401() throws Exception {
        this.mockMvc
                .perform(post("/generateToken")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new LoginParam("hacker", "secret")))
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAuthenticated_then202Admin() throws Exception {
        this.mockMvc
                .perform(post("/generateToken")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new LoginParam("admin", "secret")))
                )
                .andExpect(status().isAccepted());
    }

//    @Test
//    void whenValidInput_thenMapsToBusinessModel() throws Exception {
//        var loginParam = new LoginParam("user", "secret");
//        this.mockMvc
//                .perform(post("/login")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(loginParam)))
//                .andExpect(status().isAccepted());
//        ArgumentCaptor<LoginParam> loginParamArgumentCaptor = ArgumentCaptor.forClass(LoginParam.class);
//        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
//        verify(authenticationManager).authenticate()
//    }

}