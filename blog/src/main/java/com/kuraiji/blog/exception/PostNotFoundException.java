package com.kuraiji.blog.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PostNotFoundException extends RuntimeException {
    private final UUID id;

    public PostNotFoundException(UUID id) {
        super(String.format("Post with ID '%s' does not exist", id));
        this.id = id;
    }
}
