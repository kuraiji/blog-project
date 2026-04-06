package com.kuraiji.blog.exception;

public class UserNotFoundException extends RuntimeException {
    private final Long id;

    public UserNotFoundException(Long id) {
        super(String.format("User with ID '%d' does not exist.", id));
        this.id = id;
    }
}
