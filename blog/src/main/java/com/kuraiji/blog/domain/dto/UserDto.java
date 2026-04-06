package com.kuraiji.blog.domain.dto;

import com.kuraiji.blog.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String handle;

    private String email;

    private RoleDto role;
}
