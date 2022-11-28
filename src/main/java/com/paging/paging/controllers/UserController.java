package com.paging.paging.controllers;

import com.paging.paging.exceptions.EmptyUserRepositoryException;
import com.paging.paging.exceptions.UserDoesNotExist;
import com.paging.paging.model.User;
import com.paging.paging.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("id") Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new UserDoesNotExist(id + " is not a valid user id");
                });
    }

    @GetMapping({"/", "/all"})
    @PreAuthorize("hasAuthority('SCOPE_read')")
    @ResponseStatus(HttpStatus.OK)
    public List<User> allUsers() {
        var users = (List<User>) userRepository.findAll();
        if (users.isEmpty())
            throw new EmptyUserRepositoryException();
        return users;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_read')")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUsers(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty())
            throw new EmptyUserRepositoryException();
        return users;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}