package com.paging.paging.exceptions;

import lombok.Getter;

@Getter
public class EmptyUserRepositoryException extends RuntimeException {
    private final String message;

    public EmptyUserRepositoryException() {
        this.message = "repository is empty";
    }
}
