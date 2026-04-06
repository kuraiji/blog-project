package com.kuraiji.blog.security;


import com.kuraiji.blog.common.permissions.PermissionName;
import com.kuraiji.blog.domain.dto.PermissionDto;
import com.kuraiji.blog.domain.entity.PermissionScope;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.entity.User;
import com.kuraiji.blog.services.PermissionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class BlogUserDetails implements UserDetails {

    private final User user;

    private final PermissionService permissionService;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return user.getId();
    }

    public PermissionScope isAuthorized(PermissionName permissionName) {
        Role role = user.getRole();
        Optional<PermissionDto> permission = permissionService.findByRoleAndName(role, permissionName.getName());
        return permission.map(PermissionDto::getScope).orElse(PermissionScope.NONE);
    }
}
