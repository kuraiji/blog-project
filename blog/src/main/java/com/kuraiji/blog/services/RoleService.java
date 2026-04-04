package com.kuraiji.blog.services;

import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.request.CreateRoleRequest;

public interface RoleService {
    RoleDto createRole(CreateRoleRequest request);
}
