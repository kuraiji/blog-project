package com.kuraiji.blog.common.mappers.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements Mapper<Role, RoleDto> {

    private final ModelMapper modelMapper;

    public RoleMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDto mapTo(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public Role mapFrom(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }
}
