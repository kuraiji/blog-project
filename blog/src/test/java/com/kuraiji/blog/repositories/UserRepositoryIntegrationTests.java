package com.kuraiji.blog.repositories;

import com.kuraiji.blog.common.TestDataUtil;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.entity.User;
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@Testcontainers
public class UserRepositoryIntegrationTests {
    private final UserRepository underTest;
    private final RoleRepository roleRepo;

    @Autowired
    public UserRepositoryIntegrationTests(RoleRepository roleRepo, UserRepository underTest) {
        this.underTest = underTest;
        this.roleRepo = roleRepo;
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18-alpine");

    @Test
    public void testFindByEmail() {
        Role role = TestDataUtil.createTestRoleA();
        roleRepo.save(role);
        User user = TestDataUtil.createTestUser(role);
        underTest.save(user);
        Optional<User> result = underTest.findByEmail(user.getEmail());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void testRoleIsStoredInUser() {
        Role role = TestDataUtil.createTestRoleA();
        roleRepo.save(role);
        User user = TestDataUtil.createTestUser(role);
        User storedUser = underTest.save(user);
        Optional<User> result = underTest.findById(storedUser.getId());
        assertThat(result).isPresent();
        Role recoveredRole = result.get().getRole();
        assertThat(recoveredRole).isEqualTo(role);
    }
}
