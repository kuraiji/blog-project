package com.kuraiji.blog.common.permissions;

public interface PermissionFetcher {
    PermissionName get();
    PermissionName post();
    PermissionName patch();
    PermissionName put();
    PermissionName delete();
}
