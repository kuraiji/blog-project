package com.kuraiji.blog.controllers;

import com.kuraiji.blog.domain.dto.PermissionDto;
import com.kuraiji.blog.domain.request.CreatePermissionRequest;
import com.kuraiji.blog.exception.PermissionNotFoundException;
import com.kuraiji.blog.services.PermissionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/permissions")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping(path = "")
    public ResponseEntity<PermissionDto> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        return new ResponseEntity<>(permissionService.createPermission(request), HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public List<PermissionDto> listPermissions() {
        return permissionService.finalAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PermissionDto> getPermission(@PathVariable Short id) {
        Optional<PermissionDto> foundPermission = permissionService.findOne(id);
        return foundPermission.map(ResponseEntity::ok)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PermissionDto> fullUpdatePermission(
            @PathVariable Short id,
            @Valid @RequestBody CreatePermissionRequest request
            ) {
        if(permissionService.notExists(id)) {
            throw new PermissionNotFoundException(id);
        }
        return new ResponseEntity<>(permissionService.fullUpdatePermission(request, id), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<PermissionDto> partialUpdatePermission(
            @PathVariable Short id,
            @Valid @RequestBody CreatePermissionRequest request
    ) {
        if(permissionService.notExists(id)) {
            throw new PermissionNotFoundException(id);
        }
        return new ResponseEntity<>(permissionService.partialUpdatePermission(request, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Short id) {
        permissionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
