package com.kuraiji.blog.domain.dto;

import com.kuraiji.blog.domain.entity.PermissionScope;
import com.kuraiji.blog.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto {
    private Role role;

    private String name;

    private PermissionScope scope;
}
