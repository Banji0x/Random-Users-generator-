package com.paging.paging.exceptions;

import lombok.Getter;

@Getter
public class UserDoesNotExist extends RuntimeException {
    private final String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UserDoesNotExist(String message) {
        this.message = message;
    }
}
