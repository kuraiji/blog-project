package com.kuraiji.blog.services.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.PermissionDto;
import com.kuraiji.blog.domain.entity.Permission;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.repositories.PermissionRepository;
import com.kuraiji.blog.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final Mapper<Permission, PermissionDto> permissionMapper;

    @Override
    public Optional<PermissionDto> findByRoleAndName(Role role, String name) {
        return permissionRepository.findByRoleAndName(role, name).map(permissionMapper::mapTo);
    }
}
