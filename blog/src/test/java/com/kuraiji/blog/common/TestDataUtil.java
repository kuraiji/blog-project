package com.kuraiji.blog.common;

import com.kuraiji.blog.domain.entity.Permission;
import com.kuraiji.blog.domain.entity.PermissionScope;
import com.kuraiji.blog.domain.entity.Role;

public final class TestDataUtil {
    private TestDataUtil() {}

    public static Role createTestRole() {
        return Role.builder()
                .name("admin")
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
