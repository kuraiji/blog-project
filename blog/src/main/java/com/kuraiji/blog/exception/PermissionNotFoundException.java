package com.kuraiji.blog.exception;

import lombok.Getter;

@Getter
public class PermissionNotFoundException extends RuntimeException {

    private final Short id;

    public PermissionNotFoundException(Short id) {
        super(String.format("Permission with ID '%d' does not exist.", id));
        this.id = id;
    }
}
