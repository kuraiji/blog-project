package com.kuraiji.blog.common.permissions.impl;

import com.kuraiji.blog.common.permissions.PermissionFetcher;
import com.kuraiji.blog.common.permissions.PermissionName;

public class PostPermissionFetcher implements PermissionFetcher {
    @Override
    public PermissionName get() {
        return PermissionName.GET_POST;
    }

    @Override
    public PermissionName post() {
        return PermissionName.CREATE_POST;
    }

    @Override
    public PermissionName patch() {
        return PermissionName.EDIT_POST;
    }

    @Override
    public PermissionName put() {
        return PermissionName.EDIT_POST;
    }

    @Override
    public PermissionName delete() {
        return PermissionName.DELETE_POST;
    }
}
