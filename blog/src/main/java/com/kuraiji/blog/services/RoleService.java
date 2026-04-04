package com.kuraiji.blog.services;

import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.request.CreateRoleRequest;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleDto createRole(CreateRoleRequest request);
    List<RoleDto> findAll();
    Optional<RoleDto> findOne(Short id);
    boolean notExists(Short id);
    RoleDto fullUpdateRole(CreateRoleRequest request, Short id);
    RoleDto partialUpdateRole(CreateRoleRequest request, Short id);
    void delete(Short id);
}
