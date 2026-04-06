package com.kuraiji.blog.repositories;

import com.kuraiji.blog.common.TestDataUtil;
import com.kuraiji.blog.domain.entity.Permission;
import com.kuraiji.blog.domain.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Transactional
@DataJpaTest
@Testcontainers
public class PermissionRepositoryIntegrationTests {
    private final PermissionRepository underTest;
    private final RoleRepository roleRepository;

    @Autowired
    public PermissionRepositoryIntegrationTests(PermissionRepository underTest, RoleRepository roleRepository) {
        this.underTest = underTest;
        this.roleRepository = roleRepository;
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18-alpine");

    @Test
    public void testThatPermissionCanBeCreatedAndRecalled() {
        Role role = TestDataUtil.createTestRoleA();
        Permission permission = TestDataUtil.createTestPermission(role);
        underTest.save(permission);
        Optional<Permission> result = underTest.findById(permission.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(permission);
    }

    @Test
    public void testFindByRoleAndName() {
        Role role = TestDataUtil.createTestRoleA();
        Role savedRole = roleRepository.save(role);
        Permission permission = TestDataUtil.createTestPermission(savedRole);
        underTest.save(permission);
        Optional<Permission> result = underTest.findByRoleAndName(savedRole, permission.getName());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(permission);
    }
}
