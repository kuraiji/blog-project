package com.kuraiji.blog.exception;

public class UriNotFoundException extends RuntimeException {
    public UriNotFoundException(String message) {
        super(message);
    }
}
