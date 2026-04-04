package com.kuraiji.blog.repositories;

import com.kuraiji.blog.domain.entity.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Short> {

}
