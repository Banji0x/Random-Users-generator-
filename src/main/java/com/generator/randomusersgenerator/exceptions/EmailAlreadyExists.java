package com.generator.randomusersgenerator.exceptions;

import lombok.Getter;

@Getter
public class EmailAlreadyExists extends RuntimeException {
    private final String message;

    public EmailAlreadyExists(Long id) {
        this.message =  id + " is already a valid user";
    }
}
