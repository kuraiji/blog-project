package com.kuraiji.blog.services;

import com.kuraiji.blog.domain.dto.PermissionDto;
import com.kuraiji.blog.domain.entity.Role;

import java.util.Optional;

public interface PermissionService {
    Optional<PermissionDto> findByRoleAndName(Role role, String name);
}
