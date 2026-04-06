package com.kuraiji.blog.exception;

public class AuthorizationInvalidException extends RuntimeException {
    public AuthorizationInvalidException(String message) {
        super(message);
    }
}
