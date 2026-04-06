package com.kuraiji.blog.common.permissions.impl;

import com.kuraiji.blog.common.permissions.PermissionFetcher;
import com.kuraiji.blog.common.permissions.PermissionName;

public class PermissionPermissionFetcher implements PermissionFetcher {
    @Override
    public PermissionName get() {
        return PermissionName.GET_PERMISSION;
    }

    @Override
    public PermissionName post() {
        return PermissionName.EDIT_PERMISSION;
    }

    @Override
    public PermissionName patch() {
        return PermissionName.EDIT_PERMISSION;
    }

    @Override
    public PermissionName put() {
        return PermissionName.EDIT_PERMISSION;
    }

    @Override
    public PermissionName delete() {
        return PermissionName.DELETE_PERMISSION;
    }
}
