package com.kuraiji.blog.controllers;

import com.kuraiji.blog.domain.dto.UserDto;
import com.kuraiji.blog.domain.request.CreateUserRequest;
import com.kuraiji.blog.exception.UserNotFoundException;
import com.kuraiji.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public List<UserDto> listUsers() {
        return userService.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<UserDto> foundUser = userService.findOne(id);
        return foundUser.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> fullUpdateUser(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserRequest request
    ) {
        if(userService.notExists(id)) {
            throw new UserNotFoundException(id);
        }
        return new ResponseEntity<>(userService.fullUpdateUser(request, id), HttpStatus.OK);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> partialUpdateUser(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserRequest request
    ) {
        if(userService.notExists(id)) {
            throw new UserNotFoundException(id);
        }
        return new ResponseEntity<>(userService.partialUpdateUser(request, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
