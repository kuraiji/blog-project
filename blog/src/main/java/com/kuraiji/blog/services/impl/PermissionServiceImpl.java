package com.kuraiji.blog.services.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.PermissionDto;
import com.kuraiji.blog.domain.entity.Permission;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.request.CreatePermissionRequest;
import com.kuraiji.blog.exception.RoleNotFoundException;
import com.kuraiji.blog.repositories.PermissionRepository;
import com.kuraiji.blog.repositories.RoleRepository;
import com.kuraiji.blog.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final Mapper<Permission, PermissionDto> permissionMapper;

    private final RoleRepository roleRepository;

    @Override
    public Optional<PermissionDto> findByRoleAndName(Role role, String name) {
        return permissionRepository.findByRoleAndName(role, name).map(permissionMapper::mapTo);
    }

    @Override
    public PermissionDto createPermission(CreatePermissionRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException(request.getRoleId()));
        Permission permission = Permission.builder()
                .name(request.getName())
                .scope(request.getScope())
                .role(role)
                .build();
        return permissionMapper.mapTo(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionDto> finalAll() {
        return StreamSupport.stream(permissionRepository.findAll().spliterator(), false)
                .map(permissionMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermissionDto> findOne(Short id) {
        return permissionRepository.findById(id).map(permissionMapper::mapTo);
    }

    @Override
    public boolean notExists(Short id) {
        return !permissionRepository.existsById(id);
    }

    @Override
    public PermissionDto fullUpdatePermission(CreatePermissionRequest request, Short id) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException(request.getRoleId()));
        Permission permission = Permission.builder()
                .name(request.getName())
                .scope(request.getScope())
                .role(role)
                .build();
        return permissionMapper.mapTo(permissionRepository.save(permission));
    }

    @Override
    public PermissionDto partialUpdatePermission(CreatePermissionRequest request, Short id) {
        return permissionRepository.findById(id).map(existingPermission -> {
            Optional.ofNullable(request.getName()).ifPresent(existingPermission::setName);
            Optional.ofNullable(request.getScope()).ifPresent(existingPermission::setScope);
            Optional.ofNullable(request.getRoleId()).ifPresent(roleId -> {
                Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException(roleId));
                existingPermission.setRole(role);
            });
            return permissionMapper.mapTo(permissionRepository.save(existingPermission));
        }).orElseThrow(() -> new RuntimeException("Permission does not exist"));
    }

    @Override
    public void delete(Short id) {
        permissionRepository.deleteById(id);
    }
}
