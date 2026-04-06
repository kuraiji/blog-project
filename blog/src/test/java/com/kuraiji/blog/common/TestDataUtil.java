package com.kuraiji.blog.common;

import com.kuraiji.blog.domain.entity.Permission;
import com.kuraiji.blog.domain.entity.PermissionScope;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.entity.User;

public final class TestDataUtil {
    private TestDataUtil() {}

    public static Role createTestRoleA() {
        return Role.builder()
                .name("admin")
                .build();
    }

    public static Role createTestRoleU() {
        return Role.builder()
                .name("user")
                .build();
    }

    public static Permission createTestPermission(Role role) {
        return Permission.builder()
                .name("allowAdminAccess")
                .scope(PermissionScope.ALL)
                .role(role)
                .build();
    }

    public static User createTestUser(Role role) {
        return User.builder()
                .email("pizza@life.edu")
                .handle("pizzalover720")
                .role(role)
                .passwordHash("ThisIsAComplicatedPassword1!")
                .build();
    }
}
