package com.kuraiji.blog.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CommentNotFoundException extends RuntimeException {
    private final UUID id;

    public CommentNotFoundException(UUID id) {
        super(String.format("Comment with ID '%s' does not exist", id));
        this.id = id;
    }
}
