package com.kuraiji.blog.common.permissions.impl;

import com.kuraiji.blog.common.permissions.PermissionFetcher;
import com.kuraiji.blog.common.permissions.PermissionName;

public class UserPermissionFetcher implements PermissionFetcher {
    @Override
    public PermissionName get() {
        return PermissionName.GET_USER;
    }

    @Override
    public PermissionName post() {
        return PermissionName.CREATE_USER;
    }

    @Override
    public PermissionName patch() {
        return PermissionName.EDIT_USER;
    }

    @Override
    public PermissionName put() {
        return PermissionName.EDIT_USER;
    }

    @Override
    public PermissionName delete() {
        return PermissionName.DELETE_USER;
    }
}
