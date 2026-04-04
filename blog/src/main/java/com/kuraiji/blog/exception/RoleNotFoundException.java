package com.kuraiji.blog.exception;

import lombok.Getter;

@Getter
public class RoleNotFoundException extends RuntimeException {

    private final Short id;

    public RoleNotFoundException(Short id) {
        super(String.format("Role with ID '%d' does not exist.", id));
        this.id = id;
    }

}
