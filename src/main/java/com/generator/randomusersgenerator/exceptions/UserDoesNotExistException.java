package com.generator.randomusersgenerator.exceptions;

import lombok.Getter;

@Getter
public class UserDoesNotExistException extends RuntimeException {
    private final String message;

    public UserDoesNotExistException(Long id) {
        this.message = id + " is not a valid user id";
    }
}
