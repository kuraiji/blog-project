package com.kuraiji.blog.controllers;

import com.kuraiji.blog.common.TestDataUtil;
import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.entity.Role;
import com.kuraiji.blog.domain.entity.User;
import com.kuraiji.blog.domain.request.CreateRoleRequest;
import com.kuraiji.blog.domain.request.CreateUserRequest;
import com.kuraiji.blog.services.RoleService;
import com.kuraiji.blog.services.UserService;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
public class UserControllerIntegrationTests {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final RoleService roleService;

    private final UserService userService;

    private final Mapper<Role, RoleDto> roleMapper;

    private static final String USER_URI = "/v1/users";

    @Autowired
    public UserControllerIntegrationTests(
            MockMvc mockMvc,
            RoleService roleService,
            UserService userService,
            Mapper<Role, RoleDto> roleMapper
    ) {
        this.mockMvc = mockMvc;
        this.roleService = roleService;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
        this.roleMapper = roleMapper;
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18-alpine");

    @Test
    public void testThatCreateRoleSuccessfullyReturnsHttp201Created() throws Exception {
        Role role = TestDataUtil.createTestRoleU();
        CreateRoleRequest roleRequest = CreateRoleRequest.builder()
                        .name(role.getName()).build();
        roleService.createRole(roleRequest);
        User user = TestDataUtil.createTestUser(role);
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .email(user.getEmail())
                .handle(user.getHandle())
                .password(user.getPasswordHash())
                .build();
        String userJson = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(
                MockMvcRequestBuilders.post(USER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {
        Role role = TestDataUtil.createTestRoleU();
        CreateRoleRequest roleRequest = CreateRoleRequest.builder()
                .name(role.getName()).build();
        roleService.createRole(roleRequest);
        User user = TestDataUtil.createTestUser(role);
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .email(user.getEmail())
                .handle(user.getHandle())
                .password(user.getPasswordHash())
                .build();
        String userJson = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(
                MockMvcRequestBuilders.post(USER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.handle").value(user.getHandle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.role.name").value(role.getName())
        );
    }

    @Test
    public void testThatListUsersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(USER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListUsersReturnsListOfUsers() throws Exception {
        Role role = TestDataUtil.createTestRoleU();
        CreateRoleRequest roleRequest = CreateRoleRequest.builder()
                .name(role.getName()).build();
        Role returnedRole = roleMapper.mapFrom(roleService.createRole(roleRequest));
        User user = TestDataUtil.createTestUser(returnedRole);
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .email(user.getEmail())
                .handle(user.getHandle())
                .password(user.getPasswordHash())
                .build();
        userService.createUser(userRequest);
        mockMvc.perform(
                MockMvcRequestBuilders.get(USER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].handle").value(user.getHandle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value(user.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].role.name").value(role.getName())
        );
    }
}
