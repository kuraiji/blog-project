package com.kuraiji.blog.services.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.request.CreateRoleRequest;
import com.kuraiji.blog.repositories.RoleRepository;
import com.kuraiji.blog.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final Mapper<Role, RoleDto> roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, Mapper<Role, RoleDto> roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto createRole(CreateRoleRequest request) {
        Role role = Role.builder()
                .name(request.getName())
                .build();
        return roleMapper.mapTo(roleRepository.save(role));
    }
}
