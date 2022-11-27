package com.paging.paging.exceptions.controllerAdvice;

import com.paging.paging.controllers.UserController;
import com.paging.paging.exceptions.EmptyUserRepositoryException;
import com.paging.paging.exceptions.UserDoesNotExist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class ExceptionsHandler {

    @ExceptionHandler(UserDoesNotExist.class)
    public ResponseEntity<String> userDoesntExistHandler(UserDoesNotExist userDoesNotExist) {
        return ResponseEntity.badRequest().body("message: " + userDoesNotExist.getMessage());
    }

    @ExceptionHandler(EmptyUserRepositoryException.class)
    public ResponseEntity<String> emptyUserRepository(EmptyUserRepositoryException emptyUserRepository) {
        return ResponseEntity.badRequest().body("message: " + emptyUserRepository.getMessage());
    }

}
