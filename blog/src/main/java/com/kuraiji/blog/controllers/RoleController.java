package com.kuraiji.blog.controllers;

import com.kuraiji.blog.domain.dto.RoleDto;
import com.kuraiji.blog.domain.request.CreateRoleRequest;
import com.kuraiji.blog.exception.RoleNotFoundException;
import com.kuraiji.blog.services.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(path = "/roles")
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody CreateRoleRequest request) {
        return new ResponseEntity<>(roleService.createRole(request), HttpStatus.CREATED);
    }

    @GetMapping(path = "/roles")
    public List<RoleDto> listRoles() {
        return roleService.findAll();
    }

    @GetMapping(path = "/roles/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable Short id) {
        Optional<RoleDto> foundRole = roleService.findOne(id);
        return foundRole.map(roleDto -> new ResponseEntity<>(roleDto, HttpStatus.OK))
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    @PutMapping(path = "/roles/{id}")
    public ResponseEntity<RoleDto> fullUpdateRole(
            @PathVariable Short id,
            @Valid @RequestBody CreateRoleRequest request
        ) {
        if(roleService.notExists(id)) {
            throw new RoleNotFoundException(id);
        }
        return new ResponseEntity<>(roleService.fullUpdateRole(request, id), HttpStatus.OK);
    }

    @PatchMapping(path = "/roles/{id}")
    public ResponseEntity<RoleDto> partialUpdateRole(
            @PathVariable Short id,
            @Valid @RequestBody CreateRoleRequest request
    ) {
        if(roleService.notExists(id)) {
            throw new RoleNotFoundException(id);
        }
        return new ResponseEntity<>(roleService.partialUpdateRole(request, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Short id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
