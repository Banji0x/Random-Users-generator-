package com.generator.randomusersgenerator.controllers;

import com.generator.randomusersgenerator.exceptions.EmptyUserRepositoryException;
import com.generator.randomusersgenerator.exceptions.UserDoesNotExistException;
import com.generator.randomusersgenerator.model.User;
import com.generator.randomusersgenerator.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/users", consumes = "application/json")
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
                    throw new UserDoesNotExistException(id);
                });
    }

    @GetMapping({"/", "/all"})
    @PreAuthorize("hasAuthority('SCOPE_read')")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        var users = (List<User>) userRepository.findAll();
        if (users.isEmpty())
            throw new EmptyUserRepositoryException();
        return users;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_read')")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size) {
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
        if (!userRepository.existsById(id))
            throw new UserDoesNotExistException(id);
        userRepository.deleteById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('SCOPE_delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByIdViaParam(@RequestParam Long id) {
        if (!userRepository.existsById(id))
            throw new UserDoesNotExistException(id);
        userRepository.deleteById(id);
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            throw new EmptyUserRepositoryException();
        userRepository.deleteAll();
    }
}