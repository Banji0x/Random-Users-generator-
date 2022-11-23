package com.paging.paging.controllers;

import com.paging.paging.model.User;
import com.paging.paging.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public ResponseEntity<User> usersById(@PathVariable("id") Long id) {
        return userRepository
                .findById(id)
                .map((i) -> new ResponseEntity<>(i, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @GetMapping({"/", "/all"})
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public Iterable<User> allUsers() {
        return userRepository.findAll();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public Page<User> getUsers(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
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