package com.kuraiji.blog.common.permissions.impl;

import com.kuraiji.blog.common.permissions.PermissionFetcher;
import com.kuraiji.blog.common.permissions.PermissionName;

public class RolePermissionFetcher implements PermissionFetcher {

    @Override
    public PermissionName get() {
        return PermissionName.GET_ROLE;
    }

    @Override
    public PermissionName post() {
        return PermissionName.EDIT_ROLE;
    }

    @Override
    public PermissionName patch() {
        return PermissionName.EDIT_ROLE;
    }

    @Override
    public PermissionName put() {
        return PermissionName.EDIT_ROLE;
    }

    @Override
    public PermissionName delete() {
        return PermissionName.DELETE_ROLE;
    }
}
