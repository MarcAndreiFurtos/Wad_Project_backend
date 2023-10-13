package com.example.wad.exceptions;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public AuthenticationFailedException(String message, Exception cause) {
        super(message, cause);
    }
}
