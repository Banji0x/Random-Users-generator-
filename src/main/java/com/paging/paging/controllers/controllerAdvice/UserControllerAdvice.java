package com.paging.paging.controllers.controllerAdvice;

import com.paging.paging.controllers.UserController;
import com.paging.paging.exceptions.EmptyUserRepositoryException;
import com.paging.paging.exceptions.UserDoesNotExist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserController.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserControllerAdvice {

    @ExceptionHandler(UserDoesNotExist.class)
    public String userDoesntExistHandler(UserDoesNotExist userDoesNotExist) {
        return "message: " + userDoesNotExist.getMessage();
    }

    @ExceptionHandler(EmptyUserRepositoryException.class)
    public String emptyUserRepository(EmptyUserRepositoryException emptyUserRepository) {
        return "message: " + emptyUserRepository.getMessage();
    }

}
