package com.kuraiji.blog.services;

import com.kuraiji.blog.domain.dto.PermissionDto;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.request.CreatePermissionRequest;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    Optional<PermissionDto> findByRoleAndName(Role role, String name);
    PermissionDto createPermission(CreatePermissionRequest request);
    List<PermissionDto> finalAll();
    Optional<PermissionDto> findOne(Short id);
    boolean notExists(Short id);
    PermissionDto fullUpdatePermission(CreatePermissionRequest request, Short id);
    PermissionDto partialUpdatePermission(CreatePermissionRequest request, Short id);
    void delete(Short id);
}
