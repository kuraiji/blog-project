package com.kuraiji.blog.repositories;

import com.kuraiji.blog.common.TestDataUtil;
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
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Testcontainers
public class RoleRepositoryIntegrationTests {
    private final RoleRepository underTest;

    @Autowired
    public RoleRepositoryIntegrationTests(RoleRepository underTest) {
        this.underTest = underTest;
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18-alpine");

    @Test
    public void testThatRoleCanBeCreatedAndRecalled() {
        Role role = TestDataUtil.createTestRoleA();
        underTest.save(role);
        Optional<Role> result = underTest.findById(role.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(role);
    }
}
