package com.kuraiji.blog.controllers;

import com.kuraiji.blog.common.TestDataUtil;
import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.request.CreateRoleRequest;
import com.kuraiji.blog.services.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
public class RoleControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final RoleService roleService;

    private static final String ROLE_URI = "/v1/roles";

    @Autowired
    public RoleControllerIntegrationTests(MockMvc mockMvc, RoleService roleService) {
        this.mockMvc = mockMvc;
        this.roleService = roleService;
        this.objectMapper = new ObjectMapper();
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18-alpine");

    @Test
    public void testThatCreateRoleSuccessfullyReturnsHttp201Created() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.post(ROLE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateRoleSuccessfullyReturnsSavedRole() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.post(ROLE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("admin")
        );
    }

    @Test
    public void testThatListRolesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ROLE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListRolesReturnsListOfRoles() throws Exception {
        Role roleA = TestDataUtil.createTestRoleA();
        roleService.createRole(CreateRoleRequest.builder().name(roleA.getName()).build());
        mockMvc.perform(
                MockMvcRequestBuilders.get(ROLE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("admin")
        );
    }

    @Test
    public void testThatGetRoleReturnsHttpStatus200WhenRoleExist() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetRoleReturnsHttpStatus404WhenRoleNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("%s/1", ROLE_URI))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetRoleReturnsRoleWhenRoleExist() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(Short.toString(roleDto.getId()))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(roleDto.getName())
        );
    }

    @Test
    public void testThatFullUpdateRoleReturnsHttpStatus404WhenRoleNotExist() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("%s/1", ROLE_URI))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateRoleReturnsHttpStatus200WhenRoleExist() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        role.setName("Director");
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingRole() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        role.setName("Director");
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(Short.toString(roleDto.getId()))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(role.getName())
        );
    }

    @Test
    public void testThatPartialUpdateRoleReturnsHttpStatus404WhenRoleNotExist() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("%s/1", ROLE_URI))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateRoleReturnsHttpStatus200WhenRoleExist() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        role.setName("Director");
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPatchUpdateUpdatesExistingRole() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        role.setName("Director");
        String roleJson = objectMapper.writeValueAsString(role);
        mockMvc.perform(
                MockMvcRequestBuilders.patch(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(Short.toString(roleDto.getId()))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(role.getName())
        );
    }

    @Test
    public void testThatDeleteRoleReturnsHttpStatus204ForNonExistingRole() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("%s/1", ROLE_URI))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteRoleReturnsHttpStatus204ForExistingRole() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteRoleDeletesRole() throws Exception {
        Role role = TestDataUtil.createTestRoleA();
        RoleDto roleDto = roleService.createRole(CreateRoleRequest.builder().name(role.getName()).build());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("%s/%d", ROLE_URI, roleDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        assertThat(roleService.notExists(roleDto.getId())).isTrue();
    }
}
