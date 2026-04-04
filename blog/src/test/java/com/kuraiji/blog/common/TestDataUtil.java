package com.kuraiji.blog.common;

import com.kuraiji.blog.domain.entity.Permission;
import com.kuraiji.blog.domain.entity.PermissionScope;
import com.kuraiji.blog.domain.entity.Role;

public final class TestDataUtil {
    private TestDataUtil() {}

    public static Role createTestRoleA() {
        return Role.builder()
                .name("admin")
                .build();
    }

    public static Role createTestRoleB() {
        return Role.builder()
                .name("user")
                .build();
    }

    public static Role createTestRoleC() {
        return Role.builder()
                .name("editor")
                .build();
    }

    public static Permission createTestPermission(Role role) {
        return Permission.builder()
                .name("allowAdminAccess")
                .scope(PermissionScope.ALL)
                .role(role)
                .build();
    }
}
