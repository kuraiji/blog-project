package com.kuraiji.blog.services.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.request.CreateRoleRequest;
import com.kuraiji.blog.repositories.RoleRepository;
import com.kuraiji.blog.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<RoleDto> findAll() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(roleMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> findOne(Short id) {
        return roleRepository.findById(id).map(roleMapper::mapTo);
    }

    @Override
    public boolean notExists(Short id) {
        return !roleRepository.existsById(id);
    }

    @Override
    public RoleDto fullUpdateRole(CreateRoleRequest request, Short id) {
        Role role = Role.builder()
                .name(request.getName())
                .id(id)
                .build();
        return roleMapper.mapTo(roleRepository.save(role));
    }

    @Override
    public RoleDto partialUpdateRole(CreateRoleRequest request, Short id) {
        return roleRepository.findById(id).map(existingRole -> {
            Optional.ofNullable(request.getName()).ifPresent(existingRole::setName);
            return roleMapper.mapTo(roleRepository.save(existingRole));
        }).orElseThrow(() -> new RuntimeException("Role does not exist"));
    }

    @Override
    public void delete(Short id) {
        roleRepository.deleteById(id);
    }
}
