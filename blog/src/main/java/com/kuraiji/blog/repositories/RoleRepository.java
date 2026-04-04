package com.kuraiji.blog.repositories;

import com.kuraiji.blog.domain.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Short> {
}
