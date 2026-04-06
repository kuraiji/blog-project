package com.kuraiji.blog.repositories;

import com.kuraiji.blog.domain.entity.Permission;
import com.kuraiji.blog.domain.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Short> {
    @Query("SELECT p FROM Permission p WHERE p.role = :role AND p.name = :name")
    Optional<Permission> findByRoleAndName(
            @Param("role") Role role,
            @Param("name") String name
    );
}
