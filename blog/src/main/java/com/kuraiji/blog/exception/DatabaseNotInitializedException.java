package com.kuraiji.blog.exception;

public class DatabaseNotInitializedException extends RuntimeException {
    public DatabaseNotInitializedException(String message) {
        super(message);
    }
}
