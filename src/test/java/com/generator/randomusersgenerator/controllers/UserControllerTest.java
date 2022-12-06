package com.generator.randomusersgenerator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generator.randomusersgenerator.exceptions.EmptyUserRepositoryException;
import com.generator.randomusersgenerator.exceptions.UserDoesNotExistException;
import com.generator.randomusersgenerator.model.User;
import com.generator.randomusersgenerator.repository.UserRepository;
import com.generator.randomusersgenerator.security.SecurityChain;
import com.generator.randomusersgenerator.services.JwtTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class})
@Import({AuthController.class, JwtTokenGenerator.class, SecurityChain.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;
    @MockBean
    private UserRepository userRepository;

    //Happy Flow
    //Retrieve users by id
    @Test
    @WithUserDetails
    void getUserById_thenReturns200_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getUserById_thenReturns200(token);
    }

    @Test
    @WithUserDetails("admin")
    void getUserById_thenReturns200_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getUserById_thenReturns200(token);
    }

    //Retrieve all users
    @Test
    @WithUserDetails
    void getAllUsers_thenReturns200_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getAllUsers_thenReturns200(token);
    }

    @Test
    @WithUserDetails("admin")
    void getAllUsers_thenReturns200_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getAllUsers_thenReturns200(token);
    }

    //Retrieve users via Pagination
    @Test
    @WithUserDetails
    void getUsersViaPagination_thenReturns200_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getUsersViaPagination_thenReturns200(token);
    }

    @Test
    @WithUserDetails("admin")
    void getUsersViaPagination_thenReturns200_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getUsersViaPagination_thenReturns200(token);
    }

    //Delete Users

    @Test
    @WithUserDetails("admin")
    void deleteUsersById_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteUsersById_thenReturns400(token);
    }

    @Test
    @WithUserDetails("admin")
    void deleteUsersViaParam_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteUsersViaParam_thenReturns400(token);
    }

    @Test
    @WithUserDetails("admin")
    void deleteAllUsers_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteAllUsers_thenReturns400(token);
    }

    //Exception Tests

    //UserDoesNotExistException
    @Test
    @WithUserDetails
    void getUserById_thenReturns400_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getUserById_thenReturns400_userDoesNotExistExceptionTest(token);
    }

    @Test
    @WithUserDetails("admin")
    void getUserById_thenReturns400_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getUserById_thenReturns400_userDoesNotExistExceptionTest(token);
    }

    @Test
    @WithUserDetails("admin")
    void deleteUsersById_exceptionTest_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteUsersById_thenReturns400_userDoesNotExistExceptionTest(token);
    }

    @Test
    @WithUserDetails("admin")
    void deleteUsersViaParam_userDoesNotExistExceptionTest_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteUsersViaParam_thenReturns400_userDoesNotExistExceptionTest(token);
    }

    @Test
    @WithUserDetails("admin")
    void deleteAllUsers_userDoesNotExistExceptionTest_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteAllUsers_thenReturns400_emptyUserRepositoryExceptionTest(token);
    }


    //EmptyUserRepositoryException
    @Test
    @WithUserDetails
    void getAllUsers_exceptionTest_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getAllUsers_thenReturns400_emptyUserRepositoryExceptionTest(token);
    }

    @Test
    @WithUserDetails("admin")
    void getAllUsers_exceptionTest_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getAllUsers_thenReturns400_emptyUserRepositoryExceptionTest(token);
    }

    @Test
    @WithUserDetails
    void getAllUsersViaPagination_exceptionTest_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getAllUsersViaPagination_thenReturns400_emptyUserRepositoryExceptionTest(token);
    }

    @Test
    @WithUserDetails("admin")
    void getAllUsersViaPagination_exceptionTest_Admin() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        getAllUsersViaPagination_thenReturns400_emptyUserRepositoryExceptionTest(token);
    }

    // Access is denied Exception for User only due to insufficient privileges
    @Test
    @WithUserDetails
    public void deleteUsersById_accessIsDeniedExceptionTest_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteUserById_thenReturns400_accessIsDeniedExceptionTest(token);
    }

    @Test
    @WithUserDetails
    void deleteUserByIdViaParam_accessIsDeniedExceptionTest_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteUserByIdViaParam_thenReturns400_accessIsDeniedExceptionTest(token);
    }

    @Test
    @WithUserDetails
    void deleteAllUsers_thenReturns_400_accessIsDeniedExceptionTest_User() throws Exception {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var token = jwtTokenGenerator.generateJWTTokens(authentication);
        deleteAllUsers_thenReturns400_accessIsDeniedExceptionTest(token);
    }

    //Happy Flow Helper Methods
    void getUserById_thenReturns200(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        var content = this.mockMvc
                .perform(get("/api/users/{id}", user.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(user));

        verify(userRepository, atMostOnce()).findById(2L);
    }

    void getUsersViaPagination_thenReturns200(String token) throws Exception {
        var listOfUsers = List.of(new User(), new User(), new User(), new User(), new User(), new User(), new User());
        Pageable pageable = PageRequest.of(0, 7);
        var pageImpl = new PageImpl<>(listOfUsers, pageable, 7);

        when(userRepository.findAll(pageable))
                .thenReturn(pageImpl);
        // By parameters
        var content = this.mockMvc
                .perform(get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("page", "0")
                        .param("size", "7").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        // By default values if no parameter is provided
        var content1 = this.mockMvc
                .perform(get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(content).isEqualTo(objectMapper.writeValueAsString(pageImpl));
        assertThat(content1).isEqualTo(objectMapper.writeValueAsString(pageImpl));

        verify(userRepository, atMost(2)).findAll(pageable);
    }

    void getAllUsers_thenReturns200(String token) throws Exception {
        var listOfUsers = List.of(new User(), new User(), new User());

        when(userRepository.findAll())
                .thenReturn(listOfUsers);

        var content = this.mockMvc
                .perform(get("/api/users/").header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(listOfUsers));

        var content1 = this.mockMvc
                .perform(get("/api/users/all").header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(content1).isEqualTo(objectMapper.writeValueAsString(listOfUsers));

        verify(userRepository, atMost(2)).findAll();
    }

    void deleteUsersById_thenReturns400(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        when(userRepository.existsById(user.getId()))
                .thenReturn(true);

        var content = this.mockMvc.perform(delete("/api/users/{id}", user.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        assertThat(content).isEqualTo("");

        verify(userRepository, atMostOnce()).existsById(user.getId());
    }

    void deleteUsersViaParam_thenReturns400(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        when(userRepository.existsById(user.getId()))
                .thenReturn(true);

        var content = this.mockMvc.perform(delete("/api/users").param("id", user.getId().toString())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        assertThat(content).isEqualTo("");

        verify(userRepository, atMostOnce()).existsById(user.getId());
    }

    void deleteAllUsers_thenReturns400(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        var content = this.mockMvc.perform(delete("/api/users/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        assertThat(content).isEqualTo("");

        verify(userRepository, atMostOnce()).existsById(user.getId());
    }

    //Exception Helper Methods

    //AccessIsDeniedException
    void deleteUserById_thenReturns400_accessIsDeniedExceptionTest(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        var content = this.mockMvc.perform(delete("/api/users/{id}", user.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: Access is denied";
        assertThat(content).isEqualTo(expected);
    }

    void deleteUserByIdViaParam_thenReturns400_accessIsDeniedExceptionTest(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        var content = this.mockMvc.perform(delete("/api/users").param("id", user.getId().toString())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: Access is denied";
        assertThat(content).isEqualTo(expected);
    }

    void deleteAllUsers_thenReturns400_accessIsDeniedExceptionTest(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        var content = this.mockMvc.perform(delete("/api/users/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: Access is denied";
        assertThat(content).isEqualTo(expected);
    }

    //UserDoesNotExistException
    void getUserById_thenReturns400_userDoesNotExistExceptionTest(String token) throws Exception {
        var user = new User();
        user.setId(3L);
        when(userRepository.findById(user.getId()))
                .thenThrow(new UserDoesNotExistException(3L));

        String response = this.mockMvc.perform(get("/api/users/{id}", user.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: " + user.getId() + " is not a valid user id";
        assertThat(response).isEqualTo(expected);
        verify(userRepository, atMostOnce()).findById(3L);
    }

    void deleteUsersViaParam_thenReturns400_userDoesNotExistExceptionTest(String token) throws Exception {
        var user = new User();
        user.setId(2L);
        when(userRepository.existsById(user.getId()))
                .thenThrow(new UserDoesNotExistException(user.getId()));

        var content = this.mockMvc
                .perform(delete("/api/users").queryParam("id", user.getId().toString())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: " + user.getId() + " is not a valid user id";

        assertThat(content).isEqualTo(expected);
        verify(userRepository, atMostOnce())
                .existsById(user.getId());
    }

    void deleteUsersById_thenReturns400_userDoesNotExistExceptionTest(String token) throws Exception {
        var user = new User();
        user.setId(2L);

        when(userRepository.existsById(user.getId()))
                .thenThrow(new UserDoesNotExistException(user.getId()));

        var content = this.mockMvc.perform(delete("/api/users/{id}", user.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: " + user.getId() + " is not a valid user id";

        assertThat(content).isEqualTo(expected);
        verify(userRepository, atMostOnce())
                .existsById(user.getId());
    }

    //EmptyUserRepositoryException
    void getAllUsers_thenReturns400_emptyUserRepositoryExceptionTest(String token) throws Exception {
        when(userRepository.findAll())
                .thenThrow(new EmptyUserRepositoryException());

        String response = this.mockMvc.perform(get("/api/users/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: repository is empty";

        assertThat(response).isEqualTo(expected);

        String response2 = this.mockMvc.perform(get("/api/users/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        assertThat(response2).isEqualTo(expected);

        verify(userRepository, atMost(2)).findAll();
    }

    void getAllUsersViaPagination_thenReturns400_emptyUserRepositoryExceptionTest(String token) throws Exception {
        Pageable pageable = PageRequest.of(0, 7);

        when(userRepository.findAll(pageable))
                .thenThrow(new EmptyUserRepositoryException());

        var response = this.mockMvc
                .perform(get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "7"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: repository is empty";

        assertThat(response).isEqualTo(expected);

        String response2 = this.mockMvc
                .perform(get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        assertThat(response2).isEqualTo(expected);

        verify(userRepository, atMost(2)).findAll();
    }

    void deleteAllUsers_thenReturns400_emptyUserRepositoryExceptionTest(String token) throws Exception {

        when(userRepository.findAll())
                .thenThrow(new EmptyUserRepositoryException());

        var content = this.mockMvc.perform(delete("/api/users/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var expected = "message: repository is empty";

        assertThat(content).isEqualTo(expected);
        verify(userRepository, atMostOnce())
                .findAll();
    }
}