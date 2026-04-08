package com.kuraiji.blog.common.permissions.impl;

import com.kuraiji.blog.common.permissions.PermissionFetcher;
import com.kuraiji.blog.common.permissions.PermissionName;

public class CommentPermissionFetcher implements PermissionFetcher {
    @Override
    public PermissionName get() {
        return PermissionName.GET_COMMENT;
    }

    @Override
    public PermissionName post() {
        return PermissionName.CREATE_COMMENT;
    }

    @Override
    public PermissionName patch() {
        return PermissionName.EDIT_COMMENT;
    }

    @Override
    public PermissionName put() {
        return PermissionName.EDIT_COMMENT;
    }

    @Override
    public PermissionName delete() {
        return PermissionName.DELETE_COMMENT;
    }
}
