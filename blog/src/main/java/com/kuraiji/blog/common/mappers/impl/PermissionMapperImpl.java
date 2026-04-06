package com.kuraiji.blog.common.mappers.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.PermissionDto;
import com.kuraiji.blog.domain.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionMapperImpl implements Mapper<Permission, PermissionDto> {

    private final ModelMapper modelMapper;

    @Override
    public PermissionDto mapTo(Permission permission) {
        return modelMapper.map(permission, PermissionDto.class);
    }

    @Override
    public Permission mapFrom(PermissionDto permissionDto) {
        return modelMapper.map(permissionDto, Permission.class);
    }
}
